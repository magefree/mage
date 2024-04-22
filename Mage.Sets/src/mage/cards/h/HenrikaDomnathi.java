package mage.cards.h;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.common.BeginningOfCombatTriggeredAbility;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.LoseLifeSourceControllerEffect;
import mage.abilities.effects.common.SacrificeAllEffect;
import mage.abilities.effects.common.TransformSourceEffect;
import mage.abilities.hint.common.ModesAlreadyUsedHint;
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

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.VAMPIRE);
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);
        this.secondSideCardClazz = mage.cards.h.HenrikaInfernalSeer.class;

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // At the beginning of combat on your turn, choose one that hasn't been chosen —
        // • Each player sacrifices a creature.
        Ability ability = new BeginningOfCombatTriggeredAbility(new SacrificeAllEffect(StaticFilters.FILTER_PERMANENT_CREATURE), TargetController.YOU, false);
        ability.setModeTag("each player sacrifice");
        ability.getModes().setLimitUsageByOnce(false);

        // • You draw a card and you lose 1 life.
        Mode mode = new Mode(new DrawCardSourceControllerEffect(1).setText("you draw a card"));
        mode.addEffect(new LoseLifeSourceControllerEffect(1).concatBy("and"));
        mode.setModeTag("draw and lose life");
        ability.addMode(mode);

        // • Transform Henrika Domnathi.
        ability.addMode(new Mode(new TransformSourceEffect()).setModeTag("transform"));
        this.addAbility(new TransformAbility());

        ability.addHint(ModesAlreadyUsedHint.instance);
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
