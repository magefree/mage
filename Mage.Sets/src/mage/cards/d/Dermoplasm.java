package mage.cards.d;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.TurnedFaceUpSourceTriggeredAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.PutCardFromHandOntoBattlefieldEffect;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.abilities.keyword.MorphAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.filter.common.FilterCreatureCard;
import mage.filter.predicate.mageobject.AbilityPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author TheElk801
 */
public final class Dermoplasm extends CardImpl {

    public Dermoplasm(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}");

        this.subtype.add(SubType.SHAPESHIFTER);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Morph {2}{U}{U}
        this.addAbility(new MorphAbility(new ManaCostsImpl("{2}{U}{U}")));

        // When Dermoplasm is turned face up, you may put a creature card with a morph ability from your hand onto the battlefield face up. If you do, return Dermoplasm to its owner's hand.
        this.addAbility(new TurnedFaceUpSourceTriggeredAbility(new DermoplasmEffect()));
    }

    private Dermoplasm(final Dermoplasm card) {
        super(card);
    }

    @Override
    public Dermoplasm copy() {
        return new Dermoplasm(this);
    }
}

class DermoplasmEffect extends OneShotEffect {

    public DermoplasmEffect() {
        super(Outcome.Benefit);
        staticText = "you may put a creature card with a morph ability from your hand onto the battlefield face up. If you do, return {this} to its owner's hand";
    }

    public DermoplasmEffect(final DermoplasmEffect effect) {
        super(effect);
    }

    @Override
    public DermoplasmEffect copy() {
        return new DermoplasmEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent thisCreature = game.getPermanent(source.getId());
        FilterCreatureCard filter = new FilterCreatureCard("a creature card with a morph ability");
        filter.add(new AbilityPredicate(MorphAbility.class));
        Effect effect = new PutCardFromHandOntoBattlefieldEffect(filter);
        if (effect.apply(game, source)) {
            if (thisCreature != null) {
                effect = new ReturnToHandTargetEffect();
                effect.setTargetPointer(new FixedTarget(thisCreature.getId(), game));
                effect.apply(game, source);
                return true;
            }
        }
        return false;
    }
}
