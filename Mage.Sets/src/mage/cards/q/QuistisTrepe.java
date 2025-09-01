package mage.cards.q;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.MayCastTargetCardEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.CastManaAdjustment;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterCard;
import mage.filter.common.FilterInstantOrSorceryCard;
import mage.target.common.TargetCardInGraveyard;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class QuistisTrepe extends CardImpl {

    private static final FilterCard filter = new FilterInstantOrSorceryCard("instant or sorcery card from a graveyard");

    public QuistisTrepe(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Blue Magic -- When Quistis Trepe enters, you may cast target instant or sorcery card from a graveyard, and mana of any type can be spent to cast that spell. If that spell would be put into a graveyard, exile it instead.
        Ability ability = new EntersBattlefieldTriggeredAbility(
                new MayCastTargetCardEffect(CastManaAdjustment.AS_THOUGH_ANY_MANA_TYPE, true)
        );
        ability.addTarget(new TargetCardInGraveyard(filter));
        this.addAbility(ability.withFlavorWord("Blue Magic"));
    }

    private QuistisTrepe(final QuistisTrepe card) {
        super(card);
    }

    @Override
    public QuistisTrepe copy() {
        return new QuistisTrepe(this);
    }
}
