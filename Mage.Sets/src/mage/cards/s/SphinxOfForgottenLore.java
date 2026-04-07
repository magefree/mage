package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.effects.common.continuous.GainFlashbackTargetEffect;
import mage.abilities.keyword.FlashAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.target.common.TargetCardInYourGraveyard;

import java.util.UUID;

/**
 *
 * @author ciaccona007
 */
public final class SphinxOfForgottenLore extends CardImpl {

    public SphinxOfForgottenLore(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}{U}");

        this.subtype.add(SubType.SPHINX);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Flash
        this.addAbility(FlashAbility.getInstance());

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Whenever this creature attacks, target instant or sorcery card in your graveyard gains flashback until end of turn. The flashback cost is equal to that card's mana cost.
        Ability ability = new AttacksTriggeredAbility(new GainFlashbackTargetEffect());
        ability.addTarget(new TargetCardInYourGraveyard(StaticFilters.FILTER_CARD_INSTANT_OR_SORCERY));
        this.addAbility(ability);
    }

    private SphinxOfForgottenLore(final SphinxOfForgottenLore card) {
        super(card);
    }

    @Override
    public SphinxOfForgottenLore copy() {
        return new SphinxOfForgottenLore(this);
    }
}
