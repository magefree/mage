
package mage.cards.v;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.KickedCondition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.BoostAllEffect;
import mage.abilities.keyword.KickerAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.filter.predicate.permanent.PermanentIdPredicate;
import mage.game.Game;
import mage.game.permanent.token.SaprolingToken;

/**
 *
 * @author LevelX2
 */
public final class VerdelothTheAncient extends CardImpl {

    public VerdelothTheAncient(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{G}{G}");
        addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.TREEFOLK);

        this.power = new MageInt(4);
        this.toughness = new MageInt(7);

        // Kicker {X}
        this.addAbility(new KickerAbility("{X}"));
        
        // Saproling creatures and other Treefolk creatures get +1/+1.
        FilterCreaturePermanent filter = new FilterCreaturePermanent("Saproling creatures and other Treefolk creatures");
        filter.add(Predicates.or(
                Predicates.and(new SubtypePredicate(SubType.TREEFOLK), Predicates.not(new PermanentIdPredicate(this.getId()))),
                new SubtypePredicate(SubType.SAPROLING))
                );
        filter.add(Predicates.not(new PermanentIdPredicate(this.getId())));
                
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostAllEffect(1,1, Duration.WhileOnBattlefield, filter, false)));
        
        // When Verdeloth the Ancient enters the battlefield, if it was kicked, create X 1/1 green Saproling creature tokens.
        EntersBattlefieldTriggeredAbility ability = new EntersBattlefieldTriggeredAbility(new CreateTokenEffect(new SaprolingToken(), new GetKickerXValue()), false);
        this.addAbility(new ConditionalInterveningIfTriggeredAbility(ability, KickedCondition.instance,
                "When {this} enters the battlefield, if it was kicked, create X 1/1 green Saproling creature tokens."));
        
    }

    public VerdelothTheAncient(final VerdelothTheAncient card) {
        super(card);
    }

    @Override
    public VerdelothTheAncient copy() {
        return new VerdelothTheAncient(this);
    }
}

class GetKickerXValue implements DynamicValue {

    public GetKickerXValue() {
    }

    @Override
    public int calculate(Game game, Ability source, Effect effect) {
        int count = 0;
        Card card = game.getCard(source.getSourceId());
        if (card != null) {
            for (Ability ability: card.getAbilities()) {
                if (ability instanceof KickerAbility) {
                    count += ((KickerAbility) ability).getXManaValue();
                }
            }
        }
        return count;
    }

    @Override
    public GetKickerXValue copy() {
        return new GetKickerXValue();
    }

    @Override
    public String toString() {
        return "X";
    }

    @Override
    public String getMessage() {
        return "X";
    }
}