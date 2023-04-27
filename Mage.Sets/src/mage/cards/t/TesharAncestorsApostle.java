
package mage.cards.t;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.common.ReturnFromGraveyardToBattlefieldTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterCard;
import mage.filter.common.FilterCreatureCard;
import mage.filter.common.FilterHistoricSpell;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.target.common.TargetCardInYourGraveyard;

/**
 *
 * @author L_J
 */
public final class TesharAncestorsApostle extends CardImpl {

    private static final FilterCard filter = new FilterCreatureCard("creature card with mana value 3 or less from your graveyard");

    static {
        filter.add(new ManaValuePredicate(ComparisonType.FEWER_THAN, 4));
        filter.add(CardType.CREATURE.getPredicate());
    }

    public TesharAncestorsApostle(UUID ownerId, CardSetInfo cardSetInfo) {
        super(ownerId, cardSetInfo, new CardType[]{CardType.CREATURE}, "{3}{W}");
        addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.BIRD);
        this.subtype.add(SubType.CLERIC);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Whenever you cast a historic spell, return target creature card with converted mana cost 3 or less from your graveyard to the battlefield.
        Ability ability = new SpellCastControllerTriggeredAbility(new ReturnFromGraveyardToBattlefieldTargetEffect()
                .setText("return target creature card with mana value 3 or less from your graveyard to the battlefield. "
                        + "<i>(Artifacts, legendaries, and Sagas are historic.)</i>"), new FilterHistoricSpell(), false);
        ability.addTarget(new TargetCardInYourGraveyard(filter));
        this.addAbility(ability);

    }

    public TesharAncestorsApostle(final TesharAncestorsApostle TesharAncestorsApostle) {
        super(TesharAncestorsApostle);
    }

    public TesharAncestorsApostle copy() {
        return new TesharAncestorsApostle(this);
    }
}
