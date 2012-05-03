package com.strobel.reflection;

import com.strobel.util.ContractUtils;

/**
 * @author Mike Strobel
 */
public abstract class Binder {
    static boolean compareMethodSignatureAndName(final MethodBase m1, final MethodBase m2) {
        final ParameterCollection p1 = m1.getParameters();
        final ParameterCollection p2 = m2.getParameters();

        if (p1.size() != p2.size()) {
            return false;
        }

        for (int i = 0, n = p1.size(); i < n; i++) {
            if (!p1.get(i).getParameterType().isEquivalentTo(p2.get(i).getParameterType())) {
                return false;
            }
        }

        return true;
    }

    static MethodBase findMostDerivedNewSlotMethod(final MethodBase[] match, int cMatches) {
        throw ContractUtils.unreachable();
    }

    public abstract MethodBase selectMethod(final int bindingFlags, final MethodBase[] matches, final Type[] parameterTypes);
}