
package mage.cards.r;

import java.util.UUID;
import mage.MageInt;
import mage.MageObject;
import mage.abilities.Abilities;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.CastAsThoughItHadFlashAllEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicate;
import mage.game.Game;

/**
 *
 * @author L_J
 */
public final class RootwaterShaman extends CardImpl {

   private static final FilterCard filter = new FilterCard("Aura spells with enchant creature");
    static {
        filter.add(SubType.AURA.getPredicate());
        filter.add(new RootwaterShamanAbilityPredicate());
    }

    public RootwaterShaman(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}");
        this.subtype.add(SubType.MERFOLK);
        this.subtype.add(SubType.SHAMAN);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // You may cast Aura spells with enchant creature as though they had flash.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new CastAsThoughItHadFlashAllEffect(Duration.WhileOnBattlefield, filter, false)));
    }

    private RootwaterShaman(final RootwaterShaman card) {
        super(card);
    }

    @Override
    public RootwaterShaman copy() {
        return new RootwaterShaman(this);
    }

}

class RootwaterShamanAbilityPredicate implements Predicate<MageObject> {

    public RootwaterShamanAbilityPredicate() {
    }

    @Override
    public boolean apply(MageObject input, Game game) {
        Abilities<Ability> abilities = input.getAbilities();
        for (int i = 0; i < abilities.size(); i++) {
            if (abilities.get(i) instanceof EnchantAbility) {
                String enchantText = abilities.get(i).getRule();
                if (enchantText.contentEquals("Enchant creature")) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return "Aura card with enchant creature";
    }
}
