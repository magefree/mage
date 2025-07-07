package mage.cards.a;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.dynamicvalue.common.SourcePermanentPowerValue;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.common.FilterAttackingCreature;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;
import mage.target.targetpointer.SecondTargetPointer;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class AmyRose extends CardImpl {

    private static final FilterPermanent filter = new FilterAttackingCreature("other attacking creature");

    static {
        filter.add(AnotherPredicate.instance);
    }

    public AmyRose(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HEDGEHOG);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Haste
        this.addAbility(HasteAbility.getInstance());

        // Whenever Amy Rose attacks, attach up to one target Equipment to her. Then up to one other target attacking creature gets +X/+0 until end of turn, where X is Amy Rose's power.
        Ability ability = new AttacksTriggeredAbility(new AmyRoseEffect());
        ability.addEffect(new BoostTargetEffect(
                SourcePermanentPowerValue.NOT_NEGATIVE, StaticValue.get(0)
        ).setTargetPointer(new SecondTargetPointer())
                .setText("Then up to one other target attacking creature gets +X/+0 until end of turn, where X is {this}'s power"));
        ability.addTarget(new TargetPermanent(0, 1, StaticFilters.FILTER_PERMANENT_EQUIPMENT));
        ability.addTarget(new TargetPermanent(0, 1, filter));
        this.addAbility(ability);
    }

    private AmyRose(final AmyRose card) {
        super(card);
    }

    @Override
    public AmyRose copy() {
        return new AmyRose(this);
    }
}

class AmyRoseEffect extends OneShotEffect {

    AmyRoseEffect() {
        super(Outcome.Benefit);
        staticText = "attach up to one target Equipment to her";
    }

    private AmyRoseEffect(final AmyRoseEffect effect) {
        super(effect);
    }

    @Override
    public AmyRoseEffect copy() {
        return new AmyRoseEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = source.getSourcePermanentIfItStillExists(game);
        Permanent equipment = game.getPermanent(getTargetPointer().getFirst(game, source));
        return permanent != null && equipment != null && permanent.addAttachment(equipment.getId(), source, game);
    }
}
