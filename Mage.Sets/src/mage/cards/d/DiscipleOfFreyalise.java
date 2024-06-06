package mage.cards.d;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AsEntersBattlefieldAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.costs.common.PayLifeCost;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.TapSourceUnlessPaysEffect;
import mage.abilities.mana.GreenManaAbility;
import mage.cards.CardSetInfo;
import mage.cards.ModalDoubleFacedCard;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;

import java.util.Objects;
import java.util.UUID;

/**
 * @author Susucr
 */
public final class DiscipleOfFreyalise extends ModalDoubleFacedCard {

    public DiscipleOfFreyalise(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new CardType[]{CardType.CREATURE}, new SubType[]{SubType.ELF, SubType.DRUID}, "{3}{G}{G}{G}",
                "Garden of Freyalise", new CardType[]{CardType.LAND}, new SubType[]{}, ""
        );

        // 1.
        // Disciple of Freyalise
        // Creature — Elf Druid
        this.getLeftHalfCard().setPT(new MageInt(3), new MageInt(3));

        // When Disciple of Freyalise enters the battlefield, you may sacrifice another creature. If you do, you gain X life and draw X cards, where X is that creature's power.
        this.getLeftHalfCard().addAbility(new EntersBattlefieldTriggeredAbility(new DiscipleOfFreyaliseEffect()));

        // 2.
        // Garden of Freyalise
        // Land

        // As Garden of Freyalise enters the battlefield, you may pay 3 life. If you don't, it enters the battlefield tapped.
        this.getRightHalfCard().addAbility(new AsEntersBattlefieldAbility(
                new TapSourceUnlessPaysEffect(new PayLifeCost(3)),
                "you may pay 3 life. If you don't, it enters the battlefield tapped"
        ));

        // {T}: Add {G}.
        this.getRightHalfCard().addAbility(new GreenManaAbility());
    }

    private DiscipleOfFreyalise(final DiscipleOfFreyalise card) {
        super(card);
    }

    @Override
    public DiscipleOfFreyalise copy() {
        return new DiscipleOfFreyalise(this);
    }
}

class DiscipleOfFreyaliseEffect extends OneShotEffect {

    DiscipleOfFreyaliseEffect() {
        super(Outcome.Benefit);
        staticText = "you may sacrifice another creature. "
                + "If you do, you gain X life and draw X cards, where X is that creature's power";
    }

    private DiscipleOfFreyaliseEffect(final DiscipleOfFreyaliseEffect effect) {
        super(effect);
    }

    @Override
    public DiscipleOfFreyaliseEffect copy() {
        return new DiscipleOfFreyaliseEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        SacrificeTargetCost cost = new SacrificeTargetCost(StaticFilters.FILTER_ANOTHER_CREATURE);
        if (!cost.canPay(source, source, source.getControllerId(), game)
                || !player.chooseUse(outcome, "Sacrifice another creature?", source, game)
                || !cost.pay(source, game, source, source.getControllerId(), true)) {
            return false;
        }
        int xValue = cost
                .getPermanents()
                .stream()
                .filter(Objects::nonNull)
                .mapToInt(p -> p.getPower().getValue())
                .sum();
        if (xValue <= 0) {
            return true;
        }
        new GainLifeEffect(xValue).apply(game, source);
        new DrawCardSourceControllerEffect(xValue).apply(game, source);
        return true;
    }
}
