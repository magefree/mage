
package mage.cards.w;

import java.util.UUID;
import java.util.regex.Pattern;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.continuous.BoostEnchantedEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author L_J
 */
public final class Wordmail extends CardImpl {

    public Wordmail(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{W}");
        this.subtype.add(SubType.AURA);

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.BoostCreature));
        this.addAbility(new EnchantAbility(auraTarget));
        
        // Enchanted creature gets +1/+1 for each word in its name.
        WordmailCount count = new WordmailCount();
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostEnchantedEffect(count, count, Duration.WhileOnBattlefield)));
    }

    private Wordmail(final Wordmail card) {
        super(card);
    }

    @Override
    public Wordmail copy() {
        return new Wordmail(this);
    }
}

class WordmailCount implements DynamicValue {

    public WordmailCount() {
    }

    public WordmailCount(final WordmailCount dynamicValue) {
    }

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        Permanent aura = game.getPermanent(sourceAbility.getSourceId());
        if (aura != null) {
            Permanent permanent = game.getPermanent(aura.getAttachedTo());
            if (permanent != null) {
                return Pattern.compile("\\s+").split(permanent.getName()).length;
            }
        }
        return 0;
    }

    @Override
    public WordmailCount copy() {
        return new WordmailCount(this);
    }

    @Override
    public String toString() {
        return "1";
    }

    @Override
    public String getMessage() {
        return "word in its name";
    }
}
