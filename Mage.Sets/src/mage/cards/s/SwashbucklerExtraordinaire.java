package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksWithCreaturesTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.delayed.ReflexiveTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.DoubleStrikeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.TreasureToken;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author TheElk801
 */
public final class SwashbucklerExtraordinaire extends CardImpl {

    public SwashbucklerExtraordinaire(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}");

        this.subtype.add(SubType.DRAGON);
        this.subtype.add(SubType.ROGUE);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // When Swashbuckler Extraordinaire enters the battlefield, create a Treasure token.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new CreateTokenEffect(new TreasureToken())));

        // Whenever you attack, you may sacrifice one or more Treasures. When you do, up to that many target creatures gain double strike until end of turn.
        this.addAbility(new AttacksWithCreaturesTriggeredAbility(new SwashbucklerExtraordinaireEffect(), 1));
    }

    private SwashbucklerExtraordinaire(final SwashbucklerExtraordinaire card) {
        super(card);
    }

    @Override
    public SwashbucklerExtraordinaire copy() {
        return new SwashbucklerExtraordinaire(this);
    }
}

class SwashbucklerExtraordinaireEffect extends OneShotEffect {

    private static final FilterPermanent filter = new FilterControlledPermanent(SubType.TREASURE, "Treasures");

    SwashbucklerExtraordinaireEffect() {
        super(Outcome.Benefit);
        staticText = "you may sacrifice one or more Treasures. When you do, " +
                "up to that many target creatures gain double strike until end of turn";
    }

    private SwashbucklerExtraordinaireEffect(final SwashbucklerExtraordinaireEffect effect) {
        super(effect);
    }

    @Override
    public SwashbucklerExtraordinaireEffect copy() {
        return new SwashbucklerExtraordinaireEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        TargetPermanent target = new TargetPermanent(0, Integer.MAX_VALUE, filter, true);
        player.choose(outcome, target, source, game);
        Set<Permanent> treasures = target
                .getTargets()
                .stream()
                .map(game::getPermanent)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());
        if (treasures.isEmpty()) {
            return false;
        }
        for (Permanent permanent : treasures) {
            permanent.sacrifice(source, game);
        }
        ReflexiveTriggeredAbility ability = new ReflexiveTriggeredAbility(
                new GainAbilityTargetEffect(DoubleStrikeAbility.getInstance()), false,
                "up to that many target creatures gain double strike until end of turn"
        );
        ability.addTarget(new TargetCreaturePermanent(0, treasures.size()));
        game.fireReflexiveTriggeredAbility(ability, source);
        return true;
    }
}
