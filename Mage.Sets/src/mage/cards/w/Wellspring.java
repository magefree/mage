package mage.cards.w;

import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.UntapEnchantedEffect;
import mage.abilities.effects.common.continuous.GainControlTargetEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;
import mage.target.common.TargetLandPermanent;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class Wellspring extends CardImpl {

    public Wellspring(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{G}{W}");

        this.subtype.add(SubType.AURA);

        // Enchant land
        TargetPermanent auraTarget = new TargetLandPermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.BoostCreature));
        Ability ability = new EnchantAbility(auraTarget);
        this.addAbility(ability);

        // When Wellspring enters the battlefield, gain control of enchanted land until end of turn.
        this.addAbility(new EntersBattlefieldTriggeredAbility(
                new WellspringEffect("gain control of enchanted land until end of turn")
        ));

        // At the beginning of your upkeep, untap enchanted land. You gain control of that land until end of turn.
        ability = new BeginningOfUpkeepTriggeredAbility(
                new UntapEnchantedEffect().setText("untap enchanted land"), TargetController.YOU, false
        );
        ability.addEffect(new WellspringEffect("You gain control of that land until end of turn"));
        this.addAbility(ability);
    }

    private Wellspring(final Wellspring card) {
        super(card);
    }

    @Override
    public Wellspring copy() {
        return new Wellspring(this);
    }
}

class WellspringEffect extends OneShotEffect {

    WellspringEffect(String text) {
        super(Outcome.Benefit);
        staticText = text;
    }

    private WellspringEffect(final WellspringEffect effect) {
        super(effect);
    }

    @Override
    public WellspringEffect copy() {
        return new WellspringEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanentOrLKIBattlefield(source.getSourceId());
        if (permanent == null) {
            return false;
        }
        ContinuousEffect effect = new GainControlTargetEffect(Duration.EndOfTurn);
        effect.setTargetPointer(new FixedTarget(permanent.getAttachedTo(), game));
        game.addEffect(effect, source);
        return true;
    }
}
