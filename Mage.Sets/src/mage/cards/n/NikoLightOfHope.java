package mage.cards.n;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.delayed.AtTheBeginOfNextEndStepDelayedTriggeredAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.ReturnToBattlefieldUnderOwnerControlTargetEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.game.ExileZone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.ShardToken;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.targetpointer.FixedTarget;
import mage.util.functions.EmptyCopyApplier;

import java.util.UUID;

/**
 * @author Grath
 */
public final class NikoLightOfHope extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledCreaturePermanent("nonlegendary creature you control");

    static {
        filter.add(Predicates.not(SuperType.LEGENDARY.getPredicate()));
    }

    public NikoLightOfHope(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}{U}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // When Niko, Light of Hope enters, create two Shard tokens.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new CreateTokenEffect(new ShardToken(), 2)));

        // {2}, {T}: Exile target nonlegendary creature you control. Shards you control become copies of it until the beginning of the next end step. Return it to the battlefield under its owner's control at the beginning of the next end step.
        Ability ability = new SimpleActivatedAbility(new NikoLightOfHopeEffect(), new GenericManaCost(2));
        ability.addCost(new TapSourceCost());
        ability.addTarget(new TargetPermanent(filter));
        this.addAbility(ability);
    }

    private NikoLightOfHope(final NikoLightOfHope card) {
        super(card);
    }

    @Override
    public NikoLightOfHope copy() {
        return new NikoLightOfHope(this);
    }
}

class NikoLightOfHopeEffect extends OneShotEffect {

    NikoLightOfHopeEffect() {
        super(Outcome.Benefit);
        staticText = "Exile target nonlegendary creature you control. Shards you control become copies of it until the next end step. Return it to the battlefield under its owner's control at the beginning of the next end step.";
    }

    private NikoLightOfHopeEffect(final NikoLightOfHopeEffect effect) {
        super(effect);
    }

    @Override
    public NikoLightOfHopeEffect copy() {
        return new NikoLightOfHopeEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Permanent permanent = game.getPermanent(source.getFirstTarget());
        if (permanent == null || controller == null
                || !permanent.moveToExile(source.getSourceId(), "Niko, Light of Hope", source, game)) {
            return false;
        }
        FilterPermanent filter = new FilterPermanent("shards");
        filter.add(SubType.SHARD.getPredicate());
        for (Permanent copyTo : game.getBattlefield().getAllActivePermanents(filter, controller.getId(), game)) {
            game.copyPermanent(Duration.UntilNextEndStep, permanent, copyTo.getId(), source, new EmptyCopyApplier());
        }
        ExileZone exile = game.getExile().getExileZone(source.getSourceId());
        if (exile == null || exile.isEmpty()) {
            return true;
        }
        Card card = game.getCard(permanent.getId());
        if (card == null) {
            return true;
        }
        Effect effect = new ReturnToBattlefieldUnderOwnerControlTargetEffect(false, false);
        effect.setTargetPointer(new FixedTarget(card.getId()));
        game.addDelayedTriggeredAbility(new AtTheBeginOfNextEndStepDelayedTriggeredAbility(effect), source);
        return true;
    }

}
