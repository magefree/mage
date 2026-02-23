package mage.cards.b;

import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.DiesCreatureTriggeredAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.keyword.EarthbendTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetControlledLandPermanent;

import java.util.Optional;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BeifongsBountyHunters extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledCreaturePermanent("nonland creature you control");

    static {
        filter.add(Predicates.not(CardType.LAND.getPredicate()));
    }

    public BeifongsBountyHunters(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}{G}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.MERCENARY);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Whenever a nonland creature you control dies, earthbend X, where X is that creature's power.
        Ability ability = new DiesCreatureTriggeredAbility(
                new EarthbendTargetEffect(BeifongsBountyHuntersValue.instance, true), false, filter
        );
        ability.addTarget(new TargetControlledLandPermanent());
        this.addAbility(ability);
    }

    private BeifongsBountyHunters(final BeifongsBountyHunters card) {
        super(card);
    }

    @Override
    public BeifongsBountyHunters copy() {
        return new BeifongsBountyHunters(this);
    }
}

enum BeifongsBountyHuntersValue implements DynamicValue {
    instance;

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        return Optional
                .ofNullable((Permanent) effect.getValue("creatureDied"))
                .map(MageObject::getPower)
                .map(MageInt::getValue)
                .orElse(0);
    }

    @Override
    public BeifongsBountyHuntersValue copy() {
        return this;
    }

    @Override
    public String getMessage() {
        return "that creature's power";
    }

    @Override
    public String toString() {
        return "X";
    }
}
