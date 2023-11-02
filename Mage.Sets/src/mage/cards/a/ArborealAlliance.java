package mage.cards.a;

import mage.abilities.Ability;
import mage.abilities.common.AttacksWithCreaturesTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.dynamicvalue.common.ManacostVariableValue;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.PopulateEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.game.Game;
import mage.game.permanent.token.SylvanOfferingTreefolkToken;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class ArborealAlliance extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent(SubType.ELF, "Elves");

    public ArborealAlliance(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{X}{G}{G}");

        // When Arboreal Alliance enters the battlefield, create an X/X green Treefolk creature token.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new ArborealAllianceEffect()));

        // Whenever you attack with one or more Elves, populate.
        this.addAbility(new AttacksWithCreaturesTriggeredAbility(
                new PopulateEffect(), 1, filter
        ));
    }

    private ArborealAlliance(final ArborealAlliance card) {
        super(card);
    }

    @Override
    public ArborealAlliance copy() {
        return new ArborealAlliance(this);
    }
}

class ArborealAllianceEffect extends OneShotEffect {

    ArborealAllianceEffect() {
        super(Outcome.PutCreatureInPlay);
        staticText = "create an X/X green Treefolk creature token";
    }

    private ArborealAllianceEffect(final ArborealAllianceEffect effect) {
        super(effect);
    }

    @Override
    public ArborealAllianceEffect copy() {
        return new ArborealAllianceEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return new CreateTokenEffect(
                new SylvanOfferingTreefolkToken(ManacostVariableValue.ETB.calculate(game, source, this))
        ).apply(game, source);
    }
}