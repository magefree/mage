package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.ActivateAsSorceryActivatedAbility;
import mage.abilities.costs.common.ExileSourceFromGraveCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.keyword.SurveilEffect;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author muz
 */
public final class StoneDocent extends CardImpl {

    public StoneDocent(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}");

        this.subtype.add(SubType.SPIRIT);
        this.subtype.add(SubType.CHIMERA);
        this.power = new MageInt(3);
        this.toughness = new MageInt(1);

        // {W}, Exile this card from your graveyard: You gain 2 life. Surveil 1. Activate only as a sorcery.
        Ability ability = new ActivateAsSorceryActivatedAbility(Zone.GRAVEYARD, new GainLifeEffect(2), new ManaCostsImpl<>("{W}"));
        ability.addCost(new ExileSourceFromGraveCost());
        ability.addEffect(new SurveilEffect(1));
        this.addAbility(ability);
    }

    private StoneDocent(final StoneDocent card) {
        super(card);
    }

    @Override
    public StoneDocent copy() {
        return new StoneDocent(this);
    }
}
