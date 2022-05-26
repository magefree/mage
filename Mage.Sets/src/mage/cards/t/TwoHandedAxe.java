package mage.cards.t;

import mage.abilities.Ability;
import mage.abilities.common.AttacksAttachedTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.DoubleStrikeAbility;
import mage.abilities.keyword.EquipAbility;
import mage.cards.AdventureCard;
import mage.cards.CardSetInfo;
import mage.constants.AttachmentType;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TwoHandedAxe extends AdventureCard {

    public TwoHandedAxe(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, new CardType[]{CardType.INSTANT}, "{2}{R}", "Sweeping Cleave", "{1}{R}");

        this.subtype.add(SubType.EQUIPMENT);

        // Whenever equipped creature attacks, double its power until end of turn.
        this.addAbility(new AttacksAttachedTriggeredAbility(
                new TwoHandedAxeEffect(), AttachmentType.EQUIPMENT, false, true
        ));

        // Equip {1}{R}
        this.addAbility(new EquipAbility(Outcome.AddAbility, new ManaCostsImpl<>("{1}{R}")));

        // Sweeping Cleave
        // Target creature you control gains double strike until end of turn.
        this.getSpellCard().getSpellAbility().addEffect(new GainAbilityTargetEffect(DoubleStrikeAbility.getInstance()));
        this.getSpellCard().getSpellAbility().addTarget(new TargetControlledCreaturePermanent());
    }

    private TwoHandedAxe(final TwoHandedAxe card) {
        super(card);
    }

    @Override
    public TwoHandedAxe copy() {
        return new TwoHandedAxe(this);
    }
}

class TwoHandedAxeEffect extends OneShotEffect {

    TwoHandedAxeEffect() {
        super(Outcome.Benefit);
        staticText = "double its power until end of turn";
    }

    private TwoHandedAxeEffect(final TwoHandedAxeEffect effect) {
        super(effect);
    }

    @Override
    public TwoHandedAxeEffect copy() {
        return new TwoHandedAxeEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(getTargetPointer().getFirst(game, source));
        if (permanent == null || permanent.getPower().getValue() == 0) {
            return false;
        }
        game.addEffect(new BoostTargetEffect(
                permanent.getPower().getValue(), 0
        ).setTargetPointer(new FixedTarget(permanent, game)), source);
        return true;
    }
}
