package mage.cards.j;

import java.util.UUID;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.LandfallAbility;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.IfAbilityHasResolvedXTimesEffect;
import mage.abilities.effects.keyword.InvestigateEffect;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.watchers.common.AbilityResolvedWatcher;

/**
 *
 * @author muz
 */
public final class JakeSiskoWarCorrespondent extends CardImpl {

    public JakeSiskoWarCorrespondent(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}");
        
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.CITIZEN);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Landfall -- Whenever a land you control enters, you gain 1 life. If this is the second time this ability has resolved this turn, investigate.
        Ability ability = new LandfallAbility(new GainLifeEffect(1));
        ability.addEffect(new IfAbilityHasResolvedXTimesEffect(2, new InvestigateEffect()));
        this.addAbility(ability, new AbilityResolvedWatcher());
    }

    private JakeSiskoWarCorrespondent(final JakeSiskoWarCorrespondent card) {
        super(card);
    }

    @Override
    public JakeSiskoWarCorrespondent copy() {
        return new JakeSiskoWarCorrespondent(this);
    }
}
