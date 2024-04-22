package mage.cards.r;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.CommittedCrimeTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.DeathtouchAbility;
import mage.abilities.keyword.LifelinkAbility;
import mage.abilities.keyword.MenaceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetControlledCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class RattlebackApothecary extends CardImpl {

    public RattlebackApothecary(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}");

        this.subtype.add(SubType.GORGON);
        this.subtype.add(SubType.WARLOCK);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // Deathtouch
        this.addAbility(DeathtouchAbility.getInstance());

        // Whenever you commit a crime, target creature you control gains your choice of menace or lifelink until end of turn.
        Ability ability = new CommittedCrimeTriggeredAbility(new RattlebackApothecaryEffect());
        ability.addTarget(new TargetControlledCreaturePermanent());
        this.addAbility(ability);
    }

    private RattlebackApothecary(final RattlebackApothecary card) {
        super(card);
    }

    @Override
    public RattlebackApothecary copy() {
        return new RattlebackApothecary(this);
    }
}

class RattlebackApothecaryEffect extends OneShotEffect {

    RattlebackApothecaryEffect() {
        super(Outcome.Benefit);
        staticText = "target creature you control gains your choice of menace or lifelink until end of turn";
    }

    private RattlebackApothecaryEffect(final RattlebackApothecaryEffect effect) {
        super(effect);
    }

    @Override
    public RattlebackApothecaryEffect copy() {
        return new RattlebackApothecaryEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        Ability ability = player.chooseUse(
                outcome, "Choose menace or lifelink", null,
                "Menace", "Lifelink", source, game
        ) ? new MenaceAbility() : LifelinkAbility.getInstance();
        game.addEffect(new GainAbilityTargetEffect(ability, Duration.EndOfTurn)
                .setTargetPointer(getTargetPointer().copy()), source);
        return true;
    }
}
