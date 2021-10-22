package mage.cards.i;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.BandingAbility;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author L_J
 */
public final class IcatianSkirmishers extends CardImpl {

    public IcatianSkirmishers(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{W}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // First strike
        this.addAbility(FirstStrikeAbility.getInstance());

        // Banding
        this.addAbility(BandingAbility.getInstance());

        // Whenever Icatian Skirmishers attacks, all creatures banded with it gain first strike until end of turn.
        this.addAbility(new AttacksTriggeredAbility(new IcatianSkirmishersEffect(), false));
    }

    public IcatianSkirmishers(final IcatianSkirmishers card) {
        super(card);
    }

    @Override
    public IcatianSkirmishers copy() {
        return new IcatianSkirmishers(this);
    }

}

class IcatianSkirmishersEffect extends OneShotEffect {

    public IcatianSkirmishersEffect() {
        super(Outcome.AddAbility);
        this.staticText = "all creatures banded with it gain first strike until end of turn";
    }

    public IcatianSkirmishersEffect(final IcatianSkirmishersEffect effect) {
        super(effect);
    }

    @Override
    public IcatianSkirmishersEffect copy() {
        return new IcatianSkirmishersEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent sourcePermanent = game.getPermanent(source.getSourceId());
        if (sourcePermanent != null) {
            for (UUID bandedId : sourcePermanent.getBandedCards()) {
                Permanent banded = game.getPermanent(bandedId);
                if (banded != null 
                        && banded.getBandedCards() != null 
                        && banded.getBandedCards().contains(sourcePermanent.getId())) {
                    GainAbilityTargetEffect effect = new GainAbilityTargetEffect(FirstStrikeAbility.getInstance(), Duration.EndOfTurn);
                    effect.setTargetPointer(new FixedTarget(bandedId, game));
                    game.addEffect(effect, source);
                }
            }
            return true;
        }
        return false;
    }
}
