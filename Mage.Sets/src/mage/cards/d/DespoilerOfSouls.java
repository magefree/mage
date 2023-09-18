package mage.cards.d;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.CantBlockAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.ExileFromGraveCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.ReturnSourceFromGraveyardToBattlefieldEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.common.FilterCreatureCard;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.target.common.TargetCardInYourGraveyard;

import java.util.UUID;

/**
 * @author fireshoes
 */
public final class DespoilerOfSouls extends CardImpl {

    private static final FilterCard filter = new FilterCreatureCard("other creature cards from your graveyard");

    static {
        filter.add(AnotherPredicate.instance);
    }

    public DespoilerOfSouls(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{B}{B}");
        this.subtype.add(SubType.HORROR);
        this.power = new MageInt(3);
        this.toughness = new MageInt(1);

        // Despoiler of Souls can't block.
        this.addAbility(new CantBlockAbility());

        // {B}{B}, Exile two other creature cards from your graveyard: Return Despoiler of Souls from your graveyard to the battlefield.
        Ability ability = new SimpleActivatedAbility(
                Zone.GRAVEYARD,
                new ReturnSourceFromGraveyardToBattlefieldEffect(false, false),
                new ManaCostsImpl<>("{B}{B}")
        );
        ability.addCost(new ExileFromGraveCost(new TargetCardInYourGraveyard(2, filter)));
        this.addAbility(ability);
    }

    private DespoilerOfSouls(final DespoilerOfSouls card) {
        super(card);
    }

    @Override
    public DespoilerOfSouls copy() {
        return new DespoilerOfSouls(this);
    }
}
