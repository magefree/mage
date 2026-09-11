package mage.cards.h;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersPreparedAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.DiscardCardCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.constants.SubType;
import mage.target.common.TargetOpponent;
import mage.cards.PrepareCard;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author muz
 */
public final class HallwayHeckler extends PrepareCard {

    public HallwayHeckler(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}", "Vicious Verse", new CardType[]{CardType.SORCERY}, "{B/R}");

        this.subtype.add(SubType.ELEMENTAL);
        this.subtype.add(SubType.SORCERER);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // This creature enters prepared.
        this.addAbility(new EntersPreparedAbility());

        // {T}, Discard a card: Draw a card.
        Ability ability = new SimpleActivatedAbility(
            new DrawCardSourceControllerEffect(1),
            new TapSourceCost()
        );
        ability.addCost(new DiscardCardCost());
        this.addAbility(ability);

        // Vicious Verse
        // Sorcery {B/R}
        // Vicious Verse deals 1 damage to target opponent.
        this.getSpellCard().getSpellAbility().addTarget(new TargetOpponent());
        this.getSpellCard().getSpellAbility().addEffect(new DamageTargetEffect(1));
    }

    private HallwayHeckler(final HallwayHeckler card) {
        super(card);
    }

    @Override
    public HallwayHeckler copy() {
        return new HallwayHeckler(this);
    }
}
