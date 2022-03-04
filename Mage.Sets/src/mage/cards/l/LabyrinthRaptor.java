package mage.cards.l;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BecomesBlockedAllTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.SacrificeEffect;
import mage.abilities.effects.common.continuous.BoostAllEffect;
import mage.abilities.keyword.MenaceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.AbilityPredicate;
import mage.filter.predicate.permanent.BlockingAttackerIdPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class LabyrinthRaptor extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("a creature you control with menace");

    static {
        filter.add(new AbilityPredicate(MenaceAbility.class));
        filter.add(TargetController.YOU.getControllerPredicate());
    }

    private static final FilterCreaturePermanent filter2 = filter.copy();

    static {
        filter2.setMessage("creatures you control with menace");
    }

    public LabyrinthRaptor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{B}{R}");

        this.subtype.add(SubType.NIGHTMARE);
        this.subtype.add(SubType.DINOSAUR);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Menace
        this.addAbility(new MenaceAbility(false));

        // Whenever a creature you control with menace becomes blocked, defending player sacrifices a creature blocking it.
        this.addAbility(new BecomesBlockedAllTriggeredAbility(
                new LabyrinthRaptorEffect(), false, filter, true
        ));

        // {B}{R}: Creatures you control with menace get +1/+0 until end of turn.
        this.addAbility(new SimpleActivatedAbility(new BoostAllEffect(
                1, 0, Duration.EndOfTurn, filter2, false
        ), new ManaCostsImpl("{B}{R}")));
    }

    private LabyrinthRaptor(final LabyrinthRaptor card) {
        super(card);
    }

    @Override
    public LabyrinthRaptor copy() {
        return new LabyrinthRaptor(this);
    }
}

class LabyrinthRaptorEffect extends OneShotEffect {

    LabyrinthRaptorEffect() {
        super(Outcome.Benefit);
        staticText = "defending player sacrifices a creature blocking it";
    }

    private LabyrinthRaptorEffect(final LabyrinthRaptorEffect effect) {
        super(effect);
    }

    @Override
    public LabyrinthRaptorEffect copy() {
        return new LabyrinthRaptorEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = getTargetPointer().getFirstTargetPermanentOrLKI(game, source);
        if (permanent == null) {
            return false;
        }
        Player player = game.getPlayer(game.getCombat().getDefendingPlayerId(permanent.getId(), game));
        if (player == null) {
            return false;
        }
        FilterPermanent filterPermanent = new FilterPermanent("creature blocking " + permanent.getIdName());
        filterPermanent.add(new BlockingAttackerIdPredicate(permanent.getId()));
        Effect effect = new SacrificeEffect(filterPermanent, 1, "");
        effect.setTargetPointer(new FixedTarget(player.getId(), game));
        return effect.apply(game, source);
    }
}
