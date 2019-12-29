package mage.cards.d;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.common.EntersBattlefieldControlledTriggeredAbility;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.LoseLifeSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.target.common.TargetAnyTarget;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class DreadPresence extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledPermanent(SubType.SWAMP, "a Swamp");

    public DreadPresence(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}");

        this.subtype.add(SubType.NIGHTMARE);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Whenever a Swamp enters the battlefield under your control, choose one ---
        // • You draw a card and you lose 1 life.
        Ability ability = new EntersBattlefieldControlledTriggeredAbility(
                new DrawCardSourceControllerEffect(1).setText("you draw a card"), filter
        );
        ability.addEffect(new LoseLifeSourceControllerEffect(1).concatBy("and"));

        // • Dread Presence deals 2 damage to any target and you gain 2 life.
        Mode mode = new Mode(new DamageTargetEffect(2));
        mode.addEffect(new GainLifeEffect(2).concatBy("and"));
        mode.addTarget(new TargetAnyTarget());
        ability.addMode(mode);
        this.addAbility(ability);
    }

    private DreadPresence(final DreadPresence card) {
        super(card);
    }

    @Override
    public DreadPresence copy() {
        return new DreadPresence(this);
    }
}
