package mage.cards.t;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.common.DeliriumCondition;
import mage.abilities.dynamicvalue.common.CardTypesInGraveyardCount;
import mage.abilities.effects.common.TapTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.triggers.BeginningOfUpkeepTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AbilityWord;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreaturePermanent;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

import static mage.filter.StaticFilters.FILTER_OPPONENTS_PERMANENT_CREATURE;

/**
 * @author fireshoes
 */
public final class Topplegeist extends CardImpl {

    private static final FilterPermanent filter = new FilterCreaturePermanent("creature that player controls");

    static {
        filter.add(TargetController.ACTIVE.getControllerPredicate());
    }

    public Topplegeist(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{W}");
        this.subtype.add(SubType.SPIRIT);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // When Topplegeist enters the battlefield, tap target creature an opponent controls.
        Ability ability = new EntersBattlefieldTriggeredAbility(new TapTargetEffect());
        ability.addTarget(new TargetPermanent(FILTER_OPPONENTS_PERMANENT_CREATURE));
        this.addAbility(ability);

        // <i>Delirium</i> &mdash; At the beginning of each opponent's upkeep, if there are four or more card types among cards in your graveyard,
        // tap target creature that player controls.
        ability = new BeginningOfUpkeepTriggeredAbility(TargetController.OPPONENT, new TapTargetEffect(), false)
                .withInterveningIf(DeliriumCondition.instance);
        ability.addTarget(new TargetPermanent(filter));
        this.addAbility(ability.setAbilityWord(AbilityWord.DELIRIUM).addHint(CardTypesInGraveyardCount.YOU.getHint()));
    }

    private Topplegeist(final Topplegeist card) {
        super(card);
    }

    @Override
    public Topplegeist copy() {
        return new Topplegeist(this);
    }
}
