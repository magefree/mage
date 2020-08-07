package mage.cards.l;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AsEntersBattlefieldAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.ChooseModeEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.ProtectionAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterObject;
import mage.filter.predicate.mageobject.ConvertedManaCostParityPredicate;
import mage.game.Game;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class LavabrinkVenturer extends CardImpl {

    public LavabrinkVenturer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // As Lavabrink Venturer enters the battlefield, choose odd or even.
        this.addAbility(new AsEntersBattlefieldAbility(
                new ChooseModeEffect("Odd or even?", "Odd", "Even"),
                "choose odd or even. <i>(Zero is even.)</i>"
        ));

        // Lavabrink Venturer has protection from each converted mana cost of the chosen value.
        this.addAbility(new SimpleStaticAbility(new LavabrinkVenturerEffect()));
    }

    private LavabrinkVenturer(final LavabrinkVenturer card) {
        super(card);
    }

    @Override
    public LavabrinkVenturer copy() {
        return new LavabrinkVenturer(this);
    }
}

class LavabrinkVenturerEffect extends GainAbilitySourceEffect {

    private static final FilterObject nullFilter = new FilterObject("nothing");
    private static final FilterObject oddFilter = new FilterObject("odd converted mana costs");
    private static final FilterObject evenFilter = new FilterObject("even converted mana costs");

    static {
        nullFilter.add(ConvertedManaCostParityPredicate.ODD);
        nullFilter.add(ConvertedManaCostParityPredicate.EVEN);
        oddFilter.add(ConvertedManaCostParityPredicate.ODD);
        evenFilter.add(ConvertedManaCostParityPredicate.EVEN);
    }

    LavabrinkVenturerEffect() {
        super(new ProtectionAbility(nullFilter));
        this.ability.setRuleVisible(false);
        staticText = "{this} has protection from each converted mana cost of the chosen value. <i>(Zero is even.)</i>";
    }

    private LavabrinkVenturerEffect(final LavabrinkVenturerEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        String choosenMode = (String) game.getState().getValue(source.getSourceId() + "_modeChoice");
        if (choosenMode == null) {
            return false;
        }
        switch (choosenMode) {
            case "Odd":
                this.ability = new ProtectionAbility(oddFilter);
                break;
            case "Even":
                this.ability = new ProtectionAbility(evenFilter);
                break;
            default:
                return false;
        }
        return super.apply(game, source);
    }

    @Override
    public LavabrinkVenturerEffect copy() {
        return new LavabrinkVenturerEffect(this);
    }
}
