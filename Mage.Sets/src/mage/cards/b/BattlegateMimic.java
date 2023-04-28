
package mage.cards.b;

import java.util.UUID;
import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.effects.common.continuous.SetBasePowerToughnessSourceEffect;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubLayer;
import mage.constants.SubType;
import mage.filter.FilterSpell;
import mage.filter.predicate.mageobject.ColorPredicate;

/**
 *
 * @author jeffwadsworth
 */
public final class BattlegateMimic extends CardImpl {

    private static final FilterSpell filter = new FilterSpell("a spell that's both red and white");

    static {
        filter.add(new ColorPredicate(ObjectColor.RED));
        filter.add(new ColorPredicate(ObjectColor.WHITE));
    }

    private static final String rule = "Whenever you cast a spell that's both red and white, {this} has base power and toughness 4/2 until end of turn and gains first strike until end of turn.";

    public BattlegateMimic(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{R/W}");
        this.subtype.add(SubType.SHAPESHIFTER);

        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Whenever you cast a spell that's both red and white, Battlegate Mimic has base power and toughness 4/2 and gains first strike until end of turn.
        SetBasePowerToughnessSourceEffect baseToughnessSourceEffect = new SetBasePowerToughnessSourceEffect(4, 2, Duration.EndOfTurn, SubLayer.SetPT_7b);
        Ability ability = new SpellCastControllerTriggeredAbility(baseToughnessSourceEffect, filter, false, rule);
        ability.addEffect(new GainAbilitySourceEffect(FirstStrikeAbility.getInstance(), Duration.EndOfTurn, false, true));
        this.addAbility(ability);

    }

    private BattlegateMimic(final BattlegateMimic card) {
        super(card);
    }

    @Override
    public BattlegateMimic copy() {
        return new BattlegateMimic(this);
    }
}
