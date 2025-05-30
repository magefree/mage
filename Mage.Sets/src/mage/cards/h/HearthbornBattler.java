package mage.cards.h;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.CastSecondSpellTriggeredAbility;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.target.common.TargetOpponent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class HearthbornBattler extends CardImpl {

    public HearthbornBattler(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}");

        this.subtype.add(SubType.LIZARD);
        this.subtype.add(SubType.WARLOCK);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Haste
        this.addAbility(HasteAbility.getInstance());

        // Whenever a player casts their second spell each turn, Hearthborn Battler deals 2 damage to target opponent.
        Ability ability = new CastSecondSpellTriggeredAbility(new DamageTargetEffect(2), TargetController.ANY);
        ability.addTarget(new TargetOpponent());
        this.addAbility(ability);
    }

    private HearthbornBattler(final HearthbornBattler card) {
        super(card);
    }

    @Override
    public HearthbornBattler copy() {
        return new HearthbornBattler(this);
    }
}
