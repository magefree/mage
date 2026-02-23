package mage.cards.p;

import java.util.Optional;
import java.util.UUID;
import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.ActivateIfConditionActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.MyTurnCondition;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.abilities.effects.common.combat.CantBeBlockedByCreaturesSourceEffect;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.ObjectSourcePlayer;
import mage.filter.predicate.ObjectSourcePlayerPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;

/**
 *
 * @author muz
 */
public final class PrehistoricPet extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("creatures with greater power");

    static {
        filter.add(PrehistoricPetPredicate.instance);
    }

    public PrehistoricPet(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{W}");

        this.subtype.add(SubType.DINOSAUR);
        this.subtype.add(SubType.NINJA);
        this.power = new MageInt(1);
        this.toughness = new MageInt(2);

        // This creature can't be blocked by creatures with greater power.
        this.addAbility(new SimpleStaticAbility(new CantBeBlockedByCreaturesSourceEffect(filter, Duration.WhileOnBattlefield)));

        // {1}{W}, {T}: Return another target creature you control to its owner's hand. Activate only during your turn.
        Ability ability = new ActivateIfConditionActivatedAbility(
            new ReturnToHandTargetEffect(), new ManaCostsImpl<>("{1}{W}"), MyTurnCondition.instance
        );
        ability.addTarget(new TargetPermanent(StaticFilters.FILTER_ANOTHER_TARGET_CREATURE_YOU_CONTROL));
        this.addAbility(ability);
    }

    private PrehistoricPet(final PrehistoricPet card) {
        super(card);
    }

    @Override
    public PrehistoricPet copy() {
        return new PrehistoricPet(this);
    }
}

enum PrehistoricPetPredicate implements ObjectSourcePlayerPredicate<Permanent> {
    instance;

    @Override
    public boolean apply(ObjectSourcePlayer<Permanent> input, Game game) {
        return Optional
                .ofNullable(input.getSource().getSourcePermanentIfItStillExists(game))
                .map(MageObject::getPower)
                .map(MageInt::getValue)
                .map(x -> x < input.getObject().getPower().getValue())
                .orElse(false);
    }
}
