package mage.cards.h;

import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.EachSpellYouCastHasReplicateEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author muz
 */
public final class HughFirstOfMany extends CardImpl {

    public HughFirstOfMany(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{1}{W}{U}{B}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.BORG);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // Artifact spells you cast have replicate {2}{U}.
        this.addAbility(new SimpleStaticAbility(new EachSpellYouCastHasReplicateEffect(
                StaticFilters.FILTER_SPELL_AN_ARTIFACT, null, new ManaCostsImpl<>("{2}{U}")
        ).setText("artifact spells you cast have replicate {2}{U}. <i>"
                + "(When you cast an artifact spell, copy it for each time you paid its replicate cost. "
                + "You may choose new targets for the copies. Each copy becomes a token.)</i>")));
    }

    private HughFirstOfMany(final HughFirstOfMany card) {
        super(card);
    }

    @Override
    public HughFirstOfMany copy() {
        return new HughFirstOfMany(this);
    }
}
