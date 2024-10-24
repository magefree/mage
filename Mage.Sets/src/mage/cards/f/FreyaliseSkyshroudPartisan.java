package mage.cards.f;

import java.util.Collection;
import java.util.Objects;
import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.LoyaltyAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ConjureCardEffect;
import mage.abilities.effects.common.SeekCardEffect;
import mage.abilities.effects.common.continuous.BoostTargetPerpetuallyEffect;
import mage.cards.Card;
import mage.constants.*;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;

import mage.filter.FilterPermanent;
import mage.filter.common.FilterBySubtypeCard;
import mage.filter.common.FilterCreatureCard;
import mage.game.Game;
import mage.players.Player;
import mage.target.Target;
import mage.target.TargetPermanent;
import mage.target.targetpointer.FixedTarget;
import mage.util.RandomUtil;

/**
 *
 * @author karapuzz14
 */
public final class FreyaliseSkyshroudPartisan extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent();

    static {
        filter.add(SubType.ELF.getPredicate());
    }

    public FreyaliseSkyshroudPartisan(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.PLANESWALKER}, "{1}{G}{G}");
        
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.FREYALISE);
        this.setStartingLoyalty(4);

        // +1: Untap up to one target Elf. That Elf and a random Elf creature card in your hand perpetually get +1/+1.
        LoyaltyAbility untapAbility = new LoyaltyAbility(new FreyaliseUntapEffect(), 1);
        untapAbility.addTarget(new TargetPermanent(0, 1, filter, false));
        this.addAbility(untapAbility);

        // −1: Seek an Elf card.
        LoyaltyAbility seekAbility = new LoyaltyAbility(new SeekCardEffect(new FilterBySubtypeCard(SubType.ELF)), -1);
        this.addAbility(seekAbility);

        // −6: Conjure a Regal Force card onto the battlefield.
        LoyaltyAbility conjureAbility = new LoyaltyAbility(
                new ConjureCardEffect("Regal Force", Zone.BATTLEFIELD, 1), -6);
        this.addAbility(conjureAbility);
    }

    private FreyaliseSkyshroudPartisan(final FreyaliseSkyshroudPartisan card) {
        super(card);
    }

    @Override
    public FreyaliseSkyshroudPartisan copy() {
        return new FreyaliseSkyshroudPartisan(this);
    }
}

class FreyaliseUntapEffect extends OneShotEffect {

    private static final FilterCreatureCard filter = new FilterCreatureCard();

    static {
        filter.add(SubType.ELF.getPredicate());
    }
    FreyaliseUntapEffect() {
        super(Outcome.Untap);
        this.staticText = "Untap up to one target Elf. That Elf and a random Elf creature card in your hand perpetually get +1/+1.";
    }

    private FreyaliseUntapEffect(final FreyaliseUntapEffect effect) {
        super(effect);
    }

    @Override
    public FreyaliseUntapEffect copy() {
        return new FreyaliseUntapEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        source.getTargets()
                .stream()
                .map(Target::getTargets)
                .flatMap(Collection::stream)
                .map(game::getPermanent)
                .filter(Objects::nonNull)
                .forEachOrdered(permanent -> {
                    permanent.untap(game);
                    game.addEffect(new BoostTargetPerpetuallyEffect(1, 1).setTargetPointer(new FixedTarget(permanent, game)), source);
                });

        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null && !controller.getHand().isEmpty()) {
            Card cardFromHand = RandomUtil.randomFromCollection(controller.getHand().getCards(filter, game));
            if (cardFromHand == null) {
                return false;
            }
            game.addEffect(new BoostTargetPerpetuallyEffect(1, 1).setTargetPointer(new FixedTarget(cardFromHand, game)), source);
        }
        return true;
    }
}