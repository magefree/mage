package mage.cards.k;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.RestrictionEffect;
import mage.abilities.effects.common.continuous.BecomesCreatureTypeTargetEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.hint.common.ModesAlreadyUsedHint;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class KarganIntimidator extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent(SubType.WARRIOR, "Warrior");

    public KarganIntimidator(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(3);
        this.toughness = new MageInt(1);

        // Cowards can't block Warriors.
        this.addAbility(new SimpleStaticAbility(new KarganIntimidatorEffect()));

        // {1}: Choose one that hasn't been chosen this turn —
        // • Kargan Intimidator gets +1/+1 until end of turn.
        Ability ability = new SimpleActivatedAbility(
                new BoostSourceEffect(1, 1, Duration.EndOfTurn), new GenericManaCost(1)
        );
        ability.setModeTag("gets +1/+1");
        ability.getModes().setLimitUsageByOnce(true);

        // • Target creature becomes a Coward until end of turn.
        Mode mode = new Mode(new BecomesCreatureTypeTargetEffect(
                Duration.EndOfTurn, SubType.COWARD
        ).setText("Target creature becomes a Coward until end of turn"));
        mode.addTarget(new TargetCreaturePermanent());
        mode.setModeTag("target becomes a Coward");
        ability.addMode(mode);

        // • Target Warrior gains trample until end of turn.
        mode = new Mode(new GainAbilityTargetEffect(TrampleAbility.getInstance(), Duration.EndOfTurn));
        mode.addTarget(new TargetPermanent(filter));
        mode.setModeTag("target gain trample");
        ability.addMode(mode);

        ability.addHint(ModesAlreadyUsedHint.instance);
        this.addAbility(ability);
    }

    private KarganIntimidator(final KarganIntimidator card) {
        super(card);
    }

    @Override
    public KarganIntimidator copy() {
        return new KarganIntimidator(this);
    }
}

class KarganIntimidatorEffect extends RestrictionEffect {

    KarganIntimidatorEffect() {
        super(Duration.WhileOnBattlefield);
        staticText = "Cowards can't block Warriors";
    }

    private KarganIntimidatorEffect(final KarganIntimidatorEffect effect) {
        super(effect);
    }

    @Override
    public boolean applies(Permanent permanent, Ability source, Game game) {
        Permanent sourcePermanent = game.getPermanent(source.getSourceId());
        return sourcePermanent != null;
    }

    @Override
    public boolean canBlock(Permanent attacker, Permanent blocker, Ability source, Game game, boolean canUseChooseDialogs) {
        if (attacker != null && blocker != null) {
            Permanent sourcePermanent = game.getPermanent(source.getSourceId());
            if (sourcePermanent != null && attacker.hasSubtype(SubType.WARRIOR, game)) {
                return !blocker.hasSubtype(SubType.COWARD, game);
            }
        }
        return true;
    }

    @Override
    public KarganIntimidatorEffect copy() {
        return new KarganIntimidatorEffect(this);
    }
}
