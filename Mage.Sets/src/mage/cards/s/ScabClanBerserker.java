package mage.cards.s;

import mage.MageInt;
import mage.abilities.common.SpellCastOpponentTriggeredAbility;
import mage.abilities.condition.common.RenownedSourceCondition;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.RenownAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SetTargetPointer;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author fireshoes
 */
public final class ScabClanBerserker extends CardImpl {

    public ScabClanBerserker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}{R}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.BERSERKER);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Haste
        this.addAbility(HasteAbility.getInstance());

        // Renown 1
        this.addAbility(new RenownAbility(1));

        // Whenever an opponent casts a noncreature spell, if Scab-Clan Berserker is renowned, Scab-Clan Berserker deals 2 damage to that player.
        this.addAbility(new SpellCastOpponentTriggeredAbility(
                Zone.BATTLEFIELD,
                new DamageTargetEffect(2, true, "that player"),
                StaticFilters.FILTER_SPELL_A_NON_CREATURE, false, SetTargetPointer.PLAYER
        ).withInterveningIf(RenownedSourceCondition.THIS));
    }

    private ScabClanBerserker(final ScabClanBerserker card) {
        super(card);
    }

    @Override
    public ScabClanBerserker copy() {
        return new ScabClanBerserker(this);
    }
}
