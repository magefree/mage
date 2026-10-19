package mage.cards.d;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.ActivateOncePerGameActivatedAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenCopyTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.IndestructibleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.filter.predicate.permanent.TokenPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;

import java.util.UUID;

public final class DedicatedDollmaker extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("other nonland, nontoken permanent");
    private static final FilterControlledPermanent tokenFilter = new FilterControlledPermanent("tokens you control");

    static {
        filter.add(Predicates.not(CardType.LAND.getPredicate()));
        filter.add(TokenPredicate.FALSE);
        filter.add(AnotherPredicate.instance);
        tokenFilter.add(TokenPredicate.TRUE);
    }

    public DedicatedDollmaker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}");
        this.subtype.add(SubType.DWARF);
        this.subtype.add(SubType.ARTIFICER);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        Ability ability = new EntersBattlefieldTriggeredAbility(new DedicatedDollmakerEffect());
        ability.addTarget(new TargetPermanent(0, 1, filter, false));
        this.addAbility(ability);
        this.addAbility(new ActivateOncePerGameActivatedAbility(new GainAbilityControlledEffect(
                IndestructibleAbility.getInstance(), Duration.EndOfTurn, tokenFilter), new ManaCostsImpl<>("{1}{W}")));
    }

    private DedicatedDollmaker(final DedicatedDollmaker card) {
        super(card);
    }

    @Override
    public DedicatedDollmaker copy() {
        return new DedicatedDollmaker(this);
    }
}

class DedicatedDollmakerEffect extends OneShotEffect {

    DedicatedDollmakerEffect() {
        super(Outcome.Benefit);
        staticText = "exile up to one other target nonland, nontoken permanent. Its controller creates a token that's a copy of it, except it's not legendary and it's an artifact in addition to its other types";
    }

    private DedicatedDollmakerEffect(final DedicatedDollmakerEffect effect) {
        super(effect);
    }

    @Override
    public DedicatedDollmakerEffect copy() {
        return new DedicatedDollmakerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getFirstTarget());
        if (permanent == null) {
            return false;
        }
        Player controller = game.getPlayer(permanent.getControllerId());
        if (controller == null) {
            return false;
        }
        controller.moveCards(permanent, Zone.EXILED, source, game);
        return new CreateTokenCopyTargetEffect(controller.getId(), CardType.ARTIFACT, false)
                .setIsntLegendary(true).setSavedPermanent(permanent).apply(game, source);
    }
}
