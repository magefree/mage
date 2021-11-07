package mage.cards.h;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.common.BeginningOfCombatTriggeredAbility;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.LoseLifeSourceControllerEffect;
import mage.abilities.effects.common.SacrificeAllEffect;
import mage.abilities.effects.common.TransformSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.TransformAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.TargetController;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class HenrikaDomnathi extends CardImpl {

    public HenrikaDomnathi(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}{B}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.VAMPIRE);
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);
        this.secondSideCardClazz = mage.cards.h.HenrikaInfernalSeer.class;

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // At the beginning of combat on your turn, choose one that hasn't been chosen —
        // • Each player sacrifices a creature.
        Ability ability = new BeginningOfCombatTriggeredAbility(new SacrificeAllEffect(
                1, StaticFilters.FILTER_CONTROLLED_CREATURE_SHORT_TEXT
        ), TargetController.YOU, false);
        ability.getModes().setEachModeOnlyOnce(true);

        // • You draw a card and you lose 1 life.
        Mode mode = new Mode(new DrawCardSourceControllerEffect(1).setText("you draw a card"));
        mode.addEffect(new LoseLifeSourceControllerEffect(1).concatBy("and"));

        // • Transform Henrika Domnathi.
        ability.addEffect(new TransformSourceEffect());
        this.addAbility(new TransformAbility());
        this.addAbility(ability);
    }

    private HenrikaDomnathi(final HenrikaDomnathi card) {
        super(card);
    }

    @Override
    public HenrikaDomnathi copy() {
        return new HenrikaDomnathi(this);
    }
}
