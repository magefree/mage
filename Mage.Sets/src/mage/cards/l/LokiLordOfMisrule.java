package mage.cards.l;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.ActivateAsSorceryActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;
import mage.util.functions.RemoveTypeCopyApplier;

import java.util.UUID;

public final class LokiLordOfMisrule extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledCreaturePermanent(
            "target creature you control"
    );

    public LokiLordOfMisrule(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{U}");

        this.subtype.add(SubType.GOD);
        this.subtype.add(SubType.SORCERER);
        this.subtype.add(SubType.VILLAIN);
        this.supertype.add(SuperType.LEGENDARY);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // {U}, {T}: Choose target creature you control. Each creature you control other than
        // the chosen creature becomes a copy of that creature until end of turn, except
        // it isn't legendary. Activate only as a sorcery.
        Ability ability = new ActivateAsSorceryActivatedAbility(
                new LokiLordOfMisruleEffect(),
                new ManaCostsImpl<>("{U}")
        );
        ability.addCost(new TapSourceCost());
        ability.addTarget(new TargetPermanent(filter));
        this.addAbility(ability);
    }

    private LokiLordOfMisrule(final LokiLordOfMisrule card) {
        super(card);
    }

    @Override
    public LokiLordOfMisrule copy() {
        return new LokiLordOfMisrule(this);
    }
}

class LokiLordOfMisruleEffect extends OneShotEffect {

    LokiLordOfMisruleEffect() {
        super(Outcome.Copy);
        staticText = "choose target creature you control — each creature you control other "
                + "than the chosen creature becomes a copy of that creature until end of turn, "
                + "except it isn't legendary";
    }

    private LokiLordOfMisruleEffect(final LokiLordOfMisruleEffect effect) {
        super(effect);
    }

    @Override
    public LokiLordOfMisruleEffect copy() {
        return new LokiLordOfMisruleEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent chosenCreature = game.getPermanent(source.getFirstTarget());
        if (chosenCreature == null) {
            return false;
        }
        RemoveTypeCopyApplier applier = new RemoveTypeCopyApplier(SuperType.LEGENDARY);
        for (Permanent creature : game.getBattlefield().getActivePermanents(
                StaticFilters.FILTER_CONTROLLED_CREATURE,
                source.getControllerId(), source, game
        )) {
            if (!chosenCreature.getId().equals(creature.getId())) {
                game.copyPermanent(Duration.EndOfTurn, chosenCreature, creature.getId(), source, applier);
            }
        }
        return true;
    }
}
