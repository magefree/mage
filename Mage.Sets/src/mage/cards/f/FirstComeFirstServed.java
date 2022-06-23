
package mage.cards.f;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.UUID;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.GainAbilityAllEffect;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.filter.common.FilterAttackingOrBlockingCreature;
import mage.filter.predicate.Predicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.PermanentCard;

/**
 *
 * @author L_J
 */
public final class FirstComeFirstServed extends CardImpl {

    private static final FilterAttackingOrBlockingCreature filter = new FilterAttackingOrBlockingCreature("Each attacking or blocking creature with the lowest collector number among attacking or blocking creatures");

    static {
        filter.add(new FirstComeFirstServedPredicate());
    }

    public FirstComeFirstServed(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{1}{W}");

        // Each attacking or blocking creature with the lowest collector number among attacking or blocking creatures has first strike.
        GainAbilityAllEffect gainEffect = new GainAbilityAllEffect(FirstStrikeAbility.getInstance(), Duration.WhileOnBattlefield, filter, false);
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, gainEffect));
    }

    private FirstComeFirstServed(final FirstComeFirstServed card) {
        super(card);
    }

    @Override
    public FirstComeFirstServed copy() {
        return new FirstComeFirstServed(this);
    }
}

class FirstComeFirstServedPredicate implements Predicate<Permanent> {
    
    @Override
    public boolean apply(Permanent input, Game game) {
        if (input instanceof PermanentCard) {
            int lowestNumber = Integer.MAX_VALUE;
            for (Permanent permanent : game.getBattlefield().getAllActivePermanents(new FilterAttackingOrBlockingCreature(), game)) {
                int number = parseCardNumber(permanent);
                if (lowestNumber > number) {
                    lowestNumber = number;
                }
            }
            return parseCardNumber(input) == lowestNumber;
        }
        return false;
    }
    
    public int parseCardNumber(Permanent input) {
        String str = input.getCardNumber();
        Matcher matcher = Pattern.compile("\\d+").matcher(str);
        matcher.find();
        return Integer.parseInt(matcher.group());
    }
}
