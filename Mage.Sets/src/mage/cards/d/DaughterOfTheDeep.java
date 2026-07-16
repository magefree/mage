package mage.cards.d;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DrawNthCardTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.combat.CantBeBlockedTargetEffect;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.game.permanent.token.MerfolkToken;
import mage.target.TargetPermanent;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author muz
 */
public final class DaughterOfTheDeep extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent(SubType.MERFOLK);

    public DaughterOfTheDeep(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}");

        this.subtype.add(SubType.MERFOLK);
        this.subtype.add(SubType.NOBLE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Whenever you draw your second card each turn, create a 1/1 blue Merfolk creature token.
        this.addAbility(new DrawNthCardTriggeredAbility(
            new CreateTokenEffect(new MerfolkToken()), false, 2
        ));

        // {U}, {T}: Target Merfolk can't be blocked this turn.
        Ability ability = new SimpleActivatedAbility(new CantBeBlockedTargetEffect(), new ManaCostsImpl<>("{U}"));
        ability.addCost(new TapSourceCost());
        ability.addTarget(new TargetPermanent(filter));
        this.addAbility(ability);
    }

    private DaughterOfTheDeep(final DaughterOfTheDeep card) {
        super(card);
    }

    @Override
    public DaughterOfTheDeep copy() {
        return new DaughterOfTheDeep(this);
    }
}
