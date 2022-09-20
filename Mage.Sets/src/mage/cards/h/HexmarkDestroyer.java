package mage.cards.h;

import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.combat.CantBeBlockedByOneEffect;
import mage.abilities.keyword.UnearthAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class HexmarkDestroyer extends CardImpl {

    public HexmarkDestroyer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{4}{B}{B}");

        this.subtype.add(SubType.NECRON);
        this.power = new MageInt(6);
        this.toughness = new MageInt(6);

        // Multi-threat Eliminator -- Hexmark Destroyer can't be blocked except by six or more creatures.
        this.addAbility(new SimpleStaticAbility(
                new CantBeBlockedByOneEffect(6)
        ).withFlavorWord("Multi-threat Eliminator"));

        // Unearth {4}{B}{B}
        this.addAbility(new UnearthAbility(new ManaCostsImpl<>("{4}{B}{B}")));
    }

    private HexmarkDestroyer(final HexmarkDestroyer card) {
        super(card);
    }

    @Override
    public HexmarkDestroyer copy() {
        return new HexmarkDestroyer(this);
    }
}
