package mage.cards.i;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldOrAttacksSourceTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.CollectEvidenceCost;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.keyword.SurveilEffect;
import mage.abilities.keyword.MenaceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterPermanent;
import mage.filter.predicate.permanent.TokenPredicate;
import mage.game.permanent.token.IzoniSpiderToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class IzoniCenterOfTheWeb extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("tokens");
    static {
        filter.add(TokenPredicate.TRUE);
    }

    public IzoniCenterOfTheWeb(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{B}{G}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.DETECTIVE);
        this.power = new MageInt(5);
        this.toughness = new MageInt(4);

        // Menace
        this.addAbility(new MenaceAbility(false));

        // Whenever Izoni, Center of the Web enters the battlefield or attacks, you may collect evidence 4. If you do, create two 2/1 black and green Spider creature tokens with menace and reach.
        this.addAbility(new EntersBattlefieldOrAttacksSourceTriggeredAbility(new DoIfCostPaid(
                new CreateTokenEffect(new IzoniSpiderToken(), 2),
                new CollectEvidenceCost(4)
        )));

        // Sacrifice four tokens: Surveil 2, then draw two cards. You gain 2 life.
        Ability ability = new SimpleActivatedAbility(
                new SurveilEffect(2, false),
                new SacrificeTargetCost(4, filter)
        );
        ability.addEffect(new DrawCardSourceControllerEffect(2).concatBy(", then"));
        ability.addEffect(new GainLifeEffect(2));
        this.addAbility(ability);
    }

    private IzoniCenterOfTheWeb(final IzoniCenterOfTheWeb card) {
        super(card);
    }

    @Override
    public IzoniCenterOfTheWeb copy() {
        return new IzoniCenterOfTheWeb(this);
    }
}
