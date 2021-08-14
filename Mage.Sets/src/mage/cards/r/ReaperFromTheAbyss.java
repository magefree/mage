package mage.cards.r;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfEndStepTriggeredAbility;
import mage.abilities.condition.common.MorbidCondition;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.hint.common.MorbidHint;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author BetaSteward_at_googlemail.com
 */
public final class ReaperFromTheAbyss extends CardImpl {

    private static final FilterPermanent filter = new FilterCreaturePermanent("non-Demon creature");

    static {
        filter.add(Predicates.not(SubType.DEMON.getPredicate()));
    }

    public ReaperFromTheAbyss(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}{B}{B}");
        this.subtype.add(SubType.DEMON);

        this.power = new MageInt(6);
        this.toughness = new MageInt(6);

        this.addAbility(FlyingAbility.getInstance());
        Ability ability = new BeginningOfEndStepTriggeredAbility(
                Zone.BATTLEFIELD, new DestroyTargetEffect(),
                TargetController.ANY, MorbidCondition.instance, false
        );
        ability.addTarget(new TargetPermanent(filter));
        this.addAbility(ability.setAbilityWord(AbilityWord.MORBID).addHint(MorbidHint.instance));
    }

    private ReaperFromTheAbyss(final ReaperFromTheAbyss card) {
        super(card);
    }

    @Override
    public ReaperFromTheAbyss copy() {
        return new ReaperFromTheAbyss(this);
    }

}
