

package mage.abilities.costs.mana;

import mage.ManaSymbol;
import mage.constants.ColoredManaSymbol;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.Map;

/**
 * Represents the mana symbols on a card.
 *
 * @author noxx
 */
public class ManaSymbols extends ArrayList<ManaSymbol> {

    private static final Map<ColoredManaSymbol, ManaSymbol> coloredManaMap = new EnumMap<ColoredManaSymbol, ManaSymbol>(ColoredManaSymbol.class) {{
        put(ColoredManaSymbol.W, ManaSymbol.W);
        put(ColoredManaSymbol.U, ManaSymbol.U);
        put(ColoredManaSymbol.B, ManaSymbol.B);
        put(ColoredManaSymbol.R, ManaSymbol.R);
        put(ColoredManaSymbol.G, ManaSymbol.G);
    }};

    /**
     * Contains all possible hybrid mana costs (each represents different hybrid mana symbol)
     * We'll use it for converting from hybrid mana cost to hybrid mana symbol.
     */
    private static final Map<ManaSymbol, HybridManaCost> hybridManaMap = new EnumMap<ManaSymbol, HybridManaCost>(ManaSymbol.class) {{

    /**
     *  Build map of all possible hybrid mana symbols assigning corresponding instance of hybrid mana cost.
     */
        put(ManaSymbol.HYBRID_BG, new HybridManaCost(ColoredManaSymbol.B, ColoredManaSymbol.G));
        put(ManaSymbol.HYBRID_BR, new HybridManaCost(ColoredManaSymbol.B, ColoredManaSymbol.R));
        put(ManaSymbol.HYBRID_GU, new HybridManaCost(ColoredManaSymbol.G, ColoredManaSymbol.U));
        put(ManaSymbol.HYBRID_GW, new HybridManaCost(ColoredManaSymbol.G, ColoredManaSymbol.W));
        put(ManaSymbol.HYBRID_RG, new HybridManaCost(ColoredManaSymbol.R, ColoredManaSymbol.G));
        put(ManaSymbol.HYBRID_RW, new HybridManaCost(ColoredManaSymbol.R, ColoredManaSymbol.W));
        put(ManaSymbol.HYBRID_UB, new HybridManaCost(ColoredManaSymbol.U, ColoredManaSymbol.B));
        put(ManaSymbol.HYBRID_UR, new HybridManaCost(ColoredManaSymbol.U, ColoredManaSymbol.R));
        put(ManaSymbol.HYBRID_WB, new HybridManaCost(ColoredManaSymbol.W, ColoredManaSymbol.B));
        put(ManaSymbol.HYBRID_WU, new HybridManaCost(ColoredManaSymbol.W, ColoredManaSymbol.U));
    }};

    /**
     * Extracts mana symbols from {@link ManaCost} using {@link mage.ManaSymbol} as a base class for the mana symbols.
     *
     * @param manaCost
     * @return
     */
    public static ManaSymbols buildFromManaCost(ManaCost manaCost) {

        ManaSymbols manaSymbols = new ManaSymbols();

        if (manaCost instanceof ManaCostsImpl) {
            ManaCostsImpl manaCosts = (ManaCostsImpl)manaCost;
            for (int i = 0; i < manaCosts.size(); i++) {
                ManaCost mc = (ManaCost) manaCosts.get(i);
                if (mc instanceof ColoredManaCost) {
                    for (Map.Entry<ColoredManaSymbol, ManaSymbol> entry : coloredManaMap.entrySet()) {
                        if (mc.containsColor(entry.getKey())) {
                            manaSymbols.add(entry.getValue());
                            break;
                        }
                    }
                } else if (mc instanceof HybridManaCost) {
                    for (Map.Entry<ManaSymbol, HybridManaCost> entry : hybridManaMap.entrySet()) {
                        if (compareHybridCosts((HybridManaCost) mc, entry.getValue())) {
                            manaSymbols.add(entry.getKey());
                            break;
                        }
                    }
                }
            }
        }

        return manaSymbols;
    }

    /**
     * Compare two instance of hybrid mana cost.
     *
     * @param h1
     * @param h2
     *
     * @return
     */
    private static boolean compareHybridCosts(HybridManaCost h1, HybridManaCost h2) {
        return h1.getMana1() == h2.getMana1() && h1.getMana2() == h2.getMana2()
            || h1.getMana1() == h2.getMana2() && h1.getMana2() == h2.getMana1();
    }
}
