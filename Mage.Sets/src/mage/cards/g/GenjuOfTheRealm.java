
package mage.cards.g;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DiesAttachedTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.ReturnToHandSourceEffect;
import mage.abilities.effects.common.continuous.BecomesCreatureAttachedWithActivatedAbilityOrSpellEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.permanent.token.TokenImpl;
import mage.target.TargetPermanent;
import mage.target.common.TargetLandPermanent;

/**
 *
 * @author LevelX2
 */
public final class GenjuOfTheRealm extends CardImpl {

    public GenjuOfTheRealm(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{W}{U}{B}{R}{G}");
        this.subtype.add(SubType.AURA);
        this.supertype.add(SuperType.LEGENDARY);

        // Enchant Land
        TargetPermanent auraTarget = new TargetLandPermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.PutCreatureInPlay));
        Ability ability = new EnchantAbility(auraTarget);
        this.addAbility(ability);

        // {2}: Enchanted land becomes a legendary 8/12 Spirit creature with trample until end of turn. It's still a land.
        Ability ability2 = new SimpleActivatedAbility(Zone.BATTLEFIELD, new BecomesCreatureAttachedWithActivatedAbilityOrSpellEffect(new SpiritToken(), "Enchanted land becomes a legendary 8/12 Spirit creature with trample until end of turn. It's still a land", Duration.EndOfTurn), new GenericManaCost(2));
        this.addAbility(ability2);

        // When enchanted land is put into a graveyard, you may return Genju of the Realm from your graveyard to your hand.
        Effect effect = new ReturnToHandSourceEffect(false, true);
        effect.setText("you may return {this} from your graveyard to your hand");
        Ability ability3 = new DiesAttachedTriggeredAbility(effect, "enchanted land", true, false);
        this.addAbility(ability3);
    }

    private GenjuOfTheRealm(final GenjuOfTheRealm card) {
        super(card);
    }

    @Override
    public GenjuOfTheRealm copy() {
        return new GenjuOfTheRealm(this);
    }

    private static class SpiritToken extends TokenImpl {

        SpiritToken() {
            super("Spirit", "legendary 8/12 Spirit creature with trample");
            this.supertype.add(SuperType.LEGENDARY);
            cardType.add(CardType.CREATURE);
            this.color.setWhite(true);
            this.color.setBlue(true);
            this.color.setBlack(true);
            this.color.setRed(true);
            this.color.setGreen(true);
            subtype.add(SubType.SPIRIT);
            power = new MageInt(8);
            toughness = new MageInt(12);
            this.addAbility(TrampleAbility.getInstance());
        }

        public SpiritToken(final SpiritToken token) {
            super(token);
        }

        public SpiritToken copy() {
            return new SpiritToken(this);
        }
    }
}
