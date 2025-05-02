
package mage.cards.e;

import mage.MageInt;
import mage.MageObject;
import mage.ObjectColor;
import mage.abilities.keyword.ProtectionAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.Filter;
import mage.filter.FilterCard;
import mage.filter.FilterObject;
import mage.filter.predicate.Predicate;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 *
 * @author LevelX2
 */
public final class EmptyShrineKannushi extends CardImpl {

    public EmptyShrineKannushi(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{W}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.CLERIC);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Empty-Shrine Kannushi has protection from the colors of permanents you control.
        this.addAbility(new EmptyShrineKannushiProtectionAbility());
    }

    private EmptyShrineKannushi(final EmptyShrineKannushi card) {
        super(card);
    }

    @Override
    public EmptyShrineKannushi copy() {
        return new EmptyShrineKannushi(this);
    }
}

class EmptyShrineKannushiProtectionAbility extends ProtectionAbility {
 
    public EmptyShrineKannushiProtectionAbility() {
        super(new FilterCard());
    }

    private EmptyShrineKannushiProtectionAbility(final EmptyShrineKannushiProtectionAbility ability) {
        super(ability);
    }

    @Override
    public EmptyShrineKannushiProtectionAbility copy() {
        return new EmptyShrineKannushiProtectionAbility(this);
    }
    
    @Override
    public boolean canTarget(MageObject source, Game game) {
        ObjectColor color = new ObjectColor();
        for (Permanent permanent: game.getBattlefield().getAllActivePermanents(controllerId)) {
            ObjectColor permanentColor = permanent.getColor(game);
            if (permanentColor.isColorless()) {
                continue;
            }
            if (permanentColor.isBlack()) {
                color.setBlack(true);
            }
            if (permanentColor.isBlue()) {
                color.setBlue(true);
            }
            if (permanentColor.isGreen()) {
                color.setGreen(true);
            }
            if (permanentColor.isRed()) {
                color.setRed(true);
            }
            if (permanentColor.isWhite()) {
                color.setWhite(true);
            }
        }

        List<Predicate<MageObject>> colorPredicates = new ArrayList<>();
        if (color.isBlack()) {
            colorPredicates.add(new ColorPredicate(ObjectColor.BLACK));
        }
        if (color.isBlue()) {
            colorPredicates.add(new ColorPredicate(ObjectColor.BLUE));
        }
        if (color.isGreen()) {
            colorPredicates.add(new ColorPredicate(ObjectColor.GREEN));
        }
        if (color.isRed()) {
            colorPredicates.add(new ColorPredicate(ObjectColor.RED));
        }
        if (color.isWhite()) {
            colorPredicates.add(new ColorPredicate(ObjectColor.WHITE));
        }
        Filter protectionFilter = new FilterObject("the colors of permanents you control");
        protectionFilter.add(Predicates.or(colorPredicates));
        this.filter = protectionFilter;
        return super.canTarget(source, game);
    }

    @Override
    public String getRule() {
        return "Empty-Shrine Kannushi has protection from the colors of permanents you control.";
    }
}

