package mage.cards.k;

import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.condition.common.ControlACommanderCondition;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.BecomesCreatureTargetEffect;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.IndestructibleAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.custom.CreatureToken;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class KamahlsWill extends CardImpl {

    public KamahlsWill(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{3}{G}");

        // Choose one. If you control a commander as you cast this spell, you may choose both.
        this.getSpellAbility().getModes().setChooseText(
                "Choose one. If you control a commander as you cast this spell, you may choose both."
        );
        this.getSpellAbility().getModes().setMoreCondition(ControlACommanderCondition.instance);

        // • Until end of turn, any number of target lands you control become 1/1 Elemental creatures with vigilance, indestructible, and haste. They're still lands.
        this.getSpellAbility().addEffect(new BecomesCreatureTargetEffect(
                new CreatureToken(1, 1, "")
                        .withSubType(SubType.ELEMENTAL)
                        .withAbility(VigilanceAbility.getInstance())
                        .withAbility(IndestructibleAbility.getInstance())
                        .withAbility(HasteAbility.getInstance()),
                false, true, Duration.EndOfTurn
        ).setText("until end of turn, any number of target lands you control become 1/1 Elemental creatures " +
                "with vigilance, indestructible, and haste. They're still lands"));
        this.getSpellAbility().addTarget(new TargetPermanent(
                0, Integer.MAX_VALUE, StaticFilters.FILTER_CONTROLLED_PERMANENT_LANDS, false
        ));

        // • Choose target creature you don't control. Each creature you control deals damage equal to its power to that creature.
        Mode mode = new Mode(new KamahlsWillEffect());
        mode.addTarget(new TargetPermanent(StaticFilters.FILTER_CREATURE_YOU_DONT_CONTROL));
        this.getSpellAbility().addMode(mode);
    }

    private KamahlsWill(final KamahlsWill card) {
        super(card);
    }

    @Override
    public KamahlsWill copy() {
        return new KamahlsWill(this);
    }
}

class KamahlsWillEffect extends OneShotEffect {

    KamahlsWillEffect() {
        super(Outcome.Benefit);
        staticText = "choose target creature you don't control. " +
                "Each creature you control deals damage equal to its power to that creature";
    }

    private KamahlsWillEffect(final KamahlsWillEffect effect) {
        super(effect);
    }

    @Override
    public KamahlsWillEffect copy() {
        return new KamahlsWillEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getFirstTarget());
        if (permanent == null) {
            return false;
        }
        for (Permanent creature : game.getBattlefield().getActivePermanents(
                StaticFilters.FILTER_CONTROLLED_CREATURE,
                source.getControllerId(), source, game
        )) {
            if (creature == null || creature.getPower().getValue() < 1) {
                continue;
            }
            permanent.damage(creature.getPower().getValue(), creature.getId(), source, game);
        }
        return true;
    }
}
