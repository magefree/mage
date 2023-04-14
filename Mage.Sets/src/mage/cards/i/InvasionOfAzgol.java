package mage.cards.i;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SiegeAbility;
import mage.abilities.effects.common.LoseLifeTargetEffect;
import mage.abilities.effects.common.SacrificeEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.a.AshenReaper;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.target.TargetPlayer;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class InvasionOfAzgol extends CardImpl {

    public InvasionOfAzgol(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.BATTLE}, "{B}{R}");

        this.subtype.add(SubType.SIEGE);
        this.setStartingDefense(4);
        this.secondSideCardClazz = mage.cards.a.AshenReaper.class;

        // (As a Siege enters, choose an opponent to protect it. You and others can attack it. When it's defeated, exile it, then cast it transformed.)
        this.addAbility(new SiegeAbility());

        // When Invasion of Azgol enters the battlefield, target player sacrifices a creature or planeswalker and loses 1 life.
        Ability ability = new EntersBattlefieldTriggeredAbility(new SacrificeEffect(
                StaticFilters.FILTER_PERMANENT_CREATURE_OR_PLANESWALKER_A, 1, "target player"
        ));
        ability.addEffect(new LoseLifeTargetEffect(1).setText("and loses 1 life"));
        ability.addTarget(new TargetPlayer());
        this.addAbility(ability, AshenReaper.makeWatcher());
    }

    private InvasionOfAzgol(final InvasionOfAzgol card) {
        super(card);
    }

    @Override
    public InvasionOfAzgol copy() {
        return new InvasionOfAzgol(this);
    }
}
