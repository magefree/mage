package mage.cards.j;

import mage.MageInt;
import mage.abilities.common.DiesThisOrAnotherTriggeredAbility;
import mage.abilities.effects.common.ReturnFromGraveyardToBattlefieldTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.SubType;
import mage.filter.FilterCard;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.common.FilterCreatureCard;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.AbilityPredicate;
import mage.filter.predicate.mageobject.MageObjectReferencePredicate;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.target.common.TargetCardInYourGraveyard;

import java.util.UUID;

/**
 * @author notgreat
 */
public final class JackdawSavior extends CardImpl {
    public JackdawSavior(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}");

        this.subtype.add(SubType.BIRD);
        this.subtype.add(SubType.CLERIC);
        this.power = new MageInt(3);
        this.toughness = new MageInt(1);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Whenever Jackdaw Savior or another creature you control with flying dies, return another target creature card with lesser mana value from your graveyard to the battlefield.
        this.addAbility(new JackdawSaviorDiesThisOrAnotherTriggeredAbility());
    }

    private JackdawSavior(final JackdawSavior card) {
        super(card);
    }

    @Override
    public JackdawSavior copy() {
        return new JackdawSavior(this);
    }
}

class JackdawSaviorDiesThisOrAnotherTriggeredAbility extends DiesThisOrAnotherTriggeredAbility {
    private static final FilterControlledCreaturePermanent flyingFilter = new FilterControlledCreaturePermanent("creature you control with flying");

    static {
        flyingFilter.add(new AbilityPredicate(FlyingAbility.class));
    }

    public JackdawSaviorDiesThisOrAnotherTriggeredAbility() {
        super(new ReturnFromGraveyardToBattlefieldTargetEffect().setText(
                        "return another target creature card with lesser mana value from your graveyard to the battlefield"),
                false, flyingFilter);
    }

    protected JackdawSaviorDiesThisOrAnotherTriggeredAbility(final JackdawSaviorDiesThisOrAnotherTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public JackdawSaviorDiesThisOrAnotherTriggeredAbility copy() {
        return new JackdawSaviorDiesThisOrAnotherTriggeredAbility(this);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (super.checkTrigger(event, game)) {
            ZoneChangeEvent zEvent = (ZoneChangeEvent) event;
            FilterCard filter = new FilterCreatureCard();
            filter.add(Predicates.not(new MageObjectReferencePredicate(zEvent.getTargetId(), game)));
            filter.add(new ManaValuePredicate(ComparisonType.FEWER_THAN, zEvent.getTarget().getManaValue()));
            filter.setMessage("target creature card other than "+zEvent.getTarget().getLogName()+" with mana value less than "+zEvent.getTarget().getManaValue());
            this.getTargets().clear();
            this.addTarget(new TargetCardInYourGraveyard(filter));
            return true;
        }
        return false;
    }
}
