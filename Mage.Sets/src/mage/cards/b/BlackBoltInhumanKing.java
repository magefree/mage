package mage.cards.b;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BecomesTargetSourceTriggeredAbility;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SetTargetPointer;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.common.FilterNonlandPermanent;
import mage.target.TargetPermanent;
import mage.target.targetadjustment.ThatPlayerControlsTargetAdjuster;

import java.util.UUID;

/**
 * @author muz
 */
public final class BlackBoltInhumanKing extends CardImpl {

    private static final FilterPermanent filter = new FilterNonlandPermanent("nonland permanent that player controls");

    public BlackBoltInhumanKing(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}{U}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.INHUMAN);
        this.subtype.add(SubType.NOBLE);
        this.subtype.add(SubType.HERO);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Whenever you cast a noncreature spell, Black Bolt gets +2/+2 until end of turn.
        this.addAbility(new SpellCastControllerTriggeredAbility(
            new BoostSourceEffect(2, 2, Duration.EndOfTurn),
            StaticFilters.FILTER_SPELL_A_NON_CREATURE,
            false
        ));

        // Lethal Voice -- Whenever Black Bolt becomes the target of a spell or ability an opponent controls, destroy target nonland permanent that player controls.
        Ability ability = new BecomesTargetSourceTriggeredAbility(
            new DestroyTargetEffect(),
            StaticFilters.FILTER_SPELL_OR_ABILITY_OPPONENTS,
            SetTargetPointer.PLAYER,
            false
        ).withFlavorWord("Lethal Voice");
        ability.addTarget(new TargetPermanent(filter));
        ability.setTargetAdjuster(new ThatPlayerControlsTargetAdjuster());
        this.addAbility(ability);
    }

    private BlackBoltInhumanKing(final BlackBoltInhumanKing card) {
        super(card);
    }

    @Override
    public BlackBoltInhumanKing copy() {
        return new BlackBoltInhumanKing(this);
    }
}
