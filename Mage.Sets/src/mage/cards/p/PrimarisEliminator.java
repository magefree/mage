package mage.cards.p;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.continuous.BoostAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.filter.common.FilterCreaturePermanent;
import mage.target.TargetPlayer;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class PrimarisEliminator extends CardImpl {

    private static final FilterCreaturePermanent filter
            = new FilterCreaturePermanent("creatures target player controls");

    static {
        filter.add(TargetController.SOURCE_TARGETS.getControllerPredicate());
    }

    public PrimarisEliminator(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{B}");

        this.subtype.add(SubType.ASTARTES);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // When Primaris Eliminator enters the battlefield, choose one --
        // * Executioner Round -- Destroy target creature.
        Ability ability = new EntersBattlefieldTriggeredAbility(new DestroyTargetEffect());
        ability.addTarget(new TargetCreaturePermanent());
        ability.withFirstModeFlavorWord("Executioner Round");

        // * Hyperfang Round -- Creatures target player controls gets -2/-2 until end of turn.
        ability.addMode(new Mode(new BoostAllEffect(
                -2, -2, Duration.EndOfTurn, filter, false
        )).addTarget(new TargetPlayer()).withFlavorWord("Hyperfrag Round"));
        this.addAbility(ability);
    }

    private PrimarisEliminator(final PrimarisEliminator card) {
        super(card);
    }

    @Override
    public PrimarisEliminator copy() {
        return new PrimarisEliminator(this);
    }
}
