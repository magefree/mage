package mage.cards.b;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldAllTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.ReachAbility;
import mage.abilities.triggers.BeginningOfCombatTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.RebelRedToken;
import mage.target.TargetPermanent;
import mage.target.targetpointer.EachTargetPointer;

import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author TheElk801
 */
public final class BarretAvalancheLeader extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledPermanent(SubType.EQUIPMENT);
    private static final FilterPermanent filter2 = new FilterControlledPermanent(SubType.REBEL);

    public BarretAvalancheLeader(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}{G}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.REBEL);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Reach
        this.addAbility(ReachAbility.getInstance());

        // Avalanche! -- Whenever an Equipment you control enters, create a 2/2 red Rebel creature token.
        this.addAbility(new EntersBattlefieldAllTriggeredAbility(
                new CreateTokenEffect(new RebelRedToken()), filter
        ).withFlavorWord("Avalanche!"));

        // At the beginning of combat on your turn, attach up to one target Equipment you control to target Rebel you control.
        Ability ability = new BeginningOfCombatTriggeredAbility(new BarretAvalancheLeaderEffect());
        ability.addTarget(new TargetPermanent(0, 1, filter));
        ability.addTarget(new TargetPermanent(filter2));
        this.addAbility(ability);
    }

    private BarretAvalancheLeader(final BarretAvalancheLeader card) {
        super(card);
    }

    @Override
    public BarretAvalancheLeader copy() {
        return new BarretAvalancheLeader(this);
    }
}

class BarretAvalancheLeaderEffect extends OneShotEffect {

    BarretAvalancheLeaderEffect() {
        super(Outcome.Benefit);
        staticText = "attach up to one target Equipment you control to target Rebel you control";
        this.setTargetPointer(new EachTargetPointer());
    }

    private BarretAvalancheLeaderEffect(final BarretAvalancheLeaderEffect effect) {
        super(effect);
    }

    @Override
    public BarretAvalancheLeaderEffect copy() {
        return new BarretAvalancheLeaderEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        List<Permanent> permanents = this
                .getTargetPointer()
                .getTargets(game, source)
                .stream()
                .map(game::getPermanent)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
        return permanents.size() >= 2 && permanents.get(1).addAttachment(permanents.get(0).getId(), source, game);
    }
}
