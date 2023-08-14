package mage.cards.n;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.DiscardTargetCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.abilities.keyword.FlashAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterCard;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.common.TargetCardInHand;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class NiambiEsteemedSpeaker extends CardImpl {

    private static final FilterPermanent filter
            = new FilterControlledCreaturePermanent("another target creature you control");
    private static final FilterCard filter2 = new FilterCard("a legendary card");

    static {
        filter.add(AnotherPredicate.instance);
        filter2.add(SuperType.LEGENDARY.getPredicate());
    }

    public NiambiEsteemedSpeaker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{W}{U}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.CLERIC);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Flash
        this.addAbility(FlashAbility.getInstance());

        // When Niambi, Esteemed Speaker enters the battlefield, you may return another target creature you control to its owner's hand. If you do, you gain life equal to that creature's converted mana cost.
        Ability ability = new EntersBattlefieldTriggeredAbility(new ReturnToHandTargetEffect(), true);
        ability.addEffect(new NiambiEsteemedSpeakerEffect());
        ability.addTarget(new TargetPermanent(filter));
        this.addAbility(ability);

        // {1}{W}{U}, {T}, Discard a legendary card: Draw two cards.
        ability = new SimpleActivatedAbility(
                new DrawCardSourceControllerEffect(2), new ManaCostsImpl<>("{1}{W}{U}")
        );
        ability.addCost(new TapSourceCost());
        ability.addCost(new DiscardTargetCost(new TargetCardInHand(filter2)));
        this.addAbility(ability);
    }

    private NiambiEsteemedSpeaker(final NiambiEsteemedSpeaker card) {
        super(card);
    }

    @Override
    public NiambiEsteemedSpeaker copy() {
        return new NiambiEsteemedSpeaker(this);
    }
}

class NiambiEsteemedSpeakerEffect extends OneShotEffect {

    NiambiEsteemedSpeakerEffect() {
        super(Outcome.Benefit);
        staticText = "If you do, you gain life equal to that creature's mana value.";
    }

    private NiambiEsteemedSpeakerEffect(final NiambiEsteemedSpeakerEffect effect) {
        super(effect);
    }

    @Override
    public NiambiEsteemedSpeakerEffect copy() {
        return new NiambiEsteemedSpeakerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = getTargetPointer().getFirstTargetPermanentOrLKI(game, source);
        Player player = game.getPlayer(source.getControllerId());
        if (permanent == null || player == null) {
            return false;
        }
        return permanent.getManaValue() > 0
                && player.gainLife(permanent.getManaValue(), game, source) > 0;
    }
}