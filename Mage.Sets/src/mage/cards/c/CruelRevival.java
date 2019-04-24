package mage.cards.c;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;
import mage.target.common.TargetCardInYourGraveyard;

import java.util.UUID;

/**
 * @author jeffwadsworth
 */
public final class CruelRevival extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("non-Zombie creature");
    private static final FilterCard filter2 = new FilterCard("Zombie card from your graveyard");

    static {
        filter.add(new CardTypePredicate(CardType.CREATURE));
        filter.add(Predicates.not(new SubtypePredicate(SubType.ZOMBIE)));
        filter2.add(new SubtypePredicate(SubType.ZOMBIE));
    }

    public CruelRevival(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{4}{B}");


        // Destroy target non-Zombie creature. It can't be regenerated. Return up to one target Zombie card from your graveyard to your hand.
        this.getSpellAbility().addEffect(new CruelRevivalEffect());
        this.getSpellAbility().addTarget(new TargetPermanent(filter));
        this.getSpellAbility().addTarget(new TargetCardInYourGraveyard(0, 1, filter2));
    }

    public CruelRevival(final CruelRevival card) {
        super(card);
    }

    @Override
    public CruelRevival copy() {
        return new CruelRevival(this);
    }
}

class CruelRevivalEffect extends OneShotEffect {

    public CruelRevivalEffect() {
        super(Outcome.DestroyPermanent);
        staticText = "Destroy target non-Zombie creature. It can't be regenerated. Return up to one target Zombie card from your graveyard to your hand";
    }

    public CruelRevivalEffect(final CruelRevivalEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent targetDestroy = game.getPermanent(source.getFirstTarget());
        if (targetDestroy != null) {
            targetDestroy.destroy(source.getSourceId(), game, true);
        }

        Card targetRetrieve = game.getCard(source.getTargets().get(1).getFirstTarget());
        if (targetRetrieve != null) {
            targetRetrieve.moveToZone(Zone.HAND, source.getSourceId(), game, true);
        }
        return true;
    }

    @Override
    public CruelRevivalEffect copy() {
        return new CruelRevivalEffect(this);
    }
}