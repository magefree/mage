package mage.cards.b;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DiesCreatureTriggeredAbility;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.effects.Effects;
import mage.abilities.effects.common.ReturnFromGraveyardToBattlefieldTargetEffect;
import mage.abilities.effects.keyword.ConniveSourceEffect;
import mage.constants.ComparisonType;
import mage.constants.SubType;
import mage.abilities.keyword.DeathtouchAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.common.FilterCreatureCard;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.filter.predicate.mageobject.PowerPredicate;
import mage.filter.predicate.permanent.TokenPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCardInYourGraveyard;
import mage.target.targetadjustment.TargetAdjuster;

/**
 *
 * @author weirddan455
 */
public final class BodyLaunderer extends CardImpl {

    private static final FilterControlledCreaturePermanent filter
            = new FilterControlledCreaturePermanent("another nontoken creature you control");

    static {
        filter.add(AnotherPredicate.instance);
        filter.add(TokenPredicate.FALSE);
    }

    public BodyLaunderer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}{B}");

        this.subtype.add(SubType.OGRE);
        this.subtype.add(SubType.ROGUE);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Deathtouch
        this.addAbility(DeathtouchAbility.getInstance());

        // Whenever another nontoken creature you control dies, Body Launderer connives.
        this.addAbility(new DiesCreatureTriggeredAbility(new ConniveSourceEffect(), false, filter));

        // When Body Launderer dies, return another target non-Rogue creature card with power less than or equal to Body Launderer from your graveyard to the battlefield.
        Ability ability = new DiesSourceTriggeredAbility(new ReturnFromGraveyardToBattlefieldTargetEffect()
                .setText("return another target non-Rogue creature card with power less than or equal to {this} from your graveyard to the battlefield")
        );
        ability.setTargetAdjuster(BodyLaundererAdjuster.instance);
        this.addAbility(ability);
    }

    private BodyLaunderer(final BodyLaunderer card) {
        super(card);
    }

    @Override
    public BodyLaunderer copy() {
        return new BodyLaunderer(this);
    }
}

enum BodyLaundererAdjuster implements TargetAdjuster {
    instance;

    @Override
    public void adjustTargets(Ability ability, Game game) {
        int power = 0;
        Effects effects = ability.getEffects();
        if (!effects.isEmpty()) {
            Object died = effects.get(0).getValue("permanentLeftBattlefield");
            if (died instanceof Permanent) {
                power = ((Permanent) died).getPower().getValue();
            }
        }
        FilterCreatureCard filter = new FilterCreatureCard("another target non-Rogue creature card with power less than or equal to Body Launderer from your graveyard");
        filter.add(AnotherPredicate.instance);
        filter.add(Predicates.not(SubType.ROGUE.getPredicate()));
        filter.add(new PowerPredicate(ComparisonType.FEWER_THAN, power + 1));
        ability.getTargets().clear();
        ability.addTarget(new TargetCardInYourGraveyard(filter));
    }
}
