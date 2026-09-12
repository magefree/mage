package mage.cards.j;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksAllTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.keyword.SaddleAbility;
import mage.constants.*;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.SaddledPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.FoodToken;

/**
 *
 * @author Grath
 */
public final class JandorFortunedTraveler extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("saddled creature you control");

    static {
        filter.add(TargetController.YOU.getControllerPredicate());
        filter.add(SaddledPredicate.instance);
    }

    public JandorFortunedTraveler(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}{G}{W}");
        
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.CITIZEN);
        this.power = new MageInt(2);
        this.toughness = new MageInt(4);

        // Each Beast, Camel, Horse, Elephant, and Wolf creature you control is a Mount in addition to its other types and has saddle 2.
        this.addAbility(new SimpleStaticAbility(new JandorFortunedTravelerEffect()));

        // Whenever a saddled creature you control attacks, it gets +2/+2 until end of turn. Draw a card. Create a Food token.
        Ability ability = new AttacksAllTriggeredAbility(new BoostTargetEffect(2, 2, Duration.EndOfTurn),
                false, filter, SetTargetPointer.PERMANENT, false);
        ability.addEffect(new DrawCardSourceControllerEffect(1));
        ability.addEffect(new CreateTokenEffect(new FoodToken()));
        this.addAbility(ability);
    }

    private JandorFortunedTraveler(final JandorFortunedTraveler card) {
        super(card);
    }

    @Override
    public JandorFortunedTraveler copy() {
        return new JandorFortunedTraveler(this);
    }
}

class JandorFortunedTravelerEffect extends ContinuousEffectImpl {

    private static final FilterPermanent filter = new FilterControlledCreaturePermanent("Beast, Camel, Horse, Elephant, and Wolf creature you control");

    static {
        filter.add(Predicates.or(
                SubType.BEAST.getPredicate(),
                SubType.CAMEL.getPredicate(),
                SubType.HORSE.getPredicate(),
                SubType.ELEPHANT.getPredicate(),
                SubType.WOLF.getPredicate()
        ));
    }

    JandorFortunedTravelerEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Benefit);
        staticText = "Each Beast, Camel, Horse, Elephant, and Wolf creature you control is a Mount in addition to its " +
                "other types and has saddle 2. <i>(Tap any number of other creatures you control with total power 2 or " +
                "more: That Mount becomes saddled until end of turn. Saddle only as a sorcery.)</i>";
    }

    private JandorFortunedTravelerEffect(final JandorFortunedTravelerEffect effect) {
        super(effect);
    }

    @Override
    public JandorFortunedTravelerEffect copy() {
        return new JandorFortunedTravelerEffect(this);
    }

    @Override
    public boolean apply(Layer layer, SubLayer sublayer, Ability source, Game game) {
        for (Permanent permanent : game.getBattlefield().getActivePermanents(
                filter, source.getControllerId(), source, game)) {
            switch (layer) {
                case TypeChangingEffects_4:
                    permanent.addSubType(game, SubType.MOUNT);
                    break;
                case AbilityAddingRemovingEffects_6:
                    permanent.addAbility(new SaddleAbility(2), source.getSourceId(), game);
                    break;
            }
        }
        return true;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return false;
    }

    @Override
    public boolean hasLayer(Layer layer) {
        return layer == Layer.TypeChangingEffects_4 || layer == Layer.AbilityAddingRemovingEffects_6;
    }
}