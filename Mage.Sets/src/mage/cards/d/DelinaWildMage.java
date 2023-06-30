package mage.cards.d;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.EndOfCombatTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenCopyTargetEffect;
import mage.abilities.effects.common.ExileSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetControlledCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class DelinaWildMage extends CardImpl {

    public DelinaWildMage(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.SHAMAN);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // Whenever Delina, Wild Mage attacks, choose target creature you control, then roll a d20.
        // 1-14 | Create a tapped and attacking token that's a copy of that creature except it's not legendary and it has "Exile this creature at end of combat."
        // 15-20 | Create one of those tokens. You may roll again.
        Ability ability = new AttacksTriggeredAbility(new DelinaWildMageEffect());
        ability.addTarget(new TargetControlledCreaturePermanent());
        this.addAbility(ability);
    }

    private DelinaWildMage(final DelinaWildMage card) {
        super(card);
    }

    @Override
    public DelinaWildMage copy() {
        return new DelinaWildMage(this);
    }
}

class DelinaWildMageEffect extends OneShotEffect {

    DelinaWildMageEffect() {
        super(Outcome.Benefit);
        staticText = "choose target creature you control, then roll a d20." +
                "<br>1-14 | Create a tapped and attacking token that's a copy of that creature, " +
                "except it's not legendary and it has \"Exile this creature at end of combat.\"" +
                "<br>15-20 | Create one of those tokens. You may roll again.";
    }

    private DelinaWildMageEffect(final DelinaWildMageEffect effect) {
        super(effect);
    }

    @Override
    public DelinaWildMageEffect copy() {
        return new DelinaWildMageEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        CreateTokenCopyTargetEffect effect = new CreateTokenCopyTargetEffect(
                source.getControllerId(), null,
                false, 1, true, true
        );
        effect.setIsntLegendary(true);
        effect.addAdditionalAbilities(new EndOfCombatTriggeredAbility(
                new ExileSourceEffect(), false, "Exile this creature at end of combat."
        ));
        effect.setTargetPointer(getTargetPointer());
        while (true) {
            int result = player.rollDice(outcome, source, game, 20);
            effect.apply(game, source);
            if (result < 15 || 20 < result || !player.chooseUse(outcome, "Roll again?", source, game)) {
                break;
            }
        }
        return true;
    }
}
